package edu.cc.notecloud.view;

import edu.cc.notecloud.entity.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.shaded.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

@Named
@SessionScoped
public class AuthBean implements Serializable {

    @Inject
    UserBean userBean;

    //private String clientID = "655973556202-1bvbmcmkbqhr06vck4n42hk01fktjali.apps.googleusercontent.com";
    //private String clientSecret = "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";
    //private final String redirectUri = "http://localhost:9080/callback.xhtml";

    public void googleLogin() throws IOException {
        System.out.println("Iniciando login...");
        String estado = UUID.randomUUID().toString();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("state",estado);
        String redirect = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + URLEncoder.encode(System.getenv("CLIENT_ID"), StandardCharsets.UTF_8)
                + "&redirect_uri=" + URLEncoder.encode(System.getenv("REDIRECT_URI"), StandardCharsets.UTF_8)
                + "&response_type=code&scope=openid%20email%20profile"
                + "&state=" + URLEncoder.encode(estado, StandardCharsets.UTF_8);
        FacesContext.getCurrentInstance().getExternalContext().redirect(redirect);
    }

    private JSONObject obtenerUserInfo(String accessToken) throws IOException {
        URL urlInfo = new URL("https://openidconnect.googleapis.com/v1/userinfo");
        HttpURLConnection connInfo = (HttpURLConnection) urlInfo.openConnection();
        connInfo.setRequestProperty("Authorization", "Bearer " + accessToken);

        InputStream is = connInfo.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder jsonResponse = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonResponse.append(line);
        }

        // Cerrar streams
        reader.close();
        is.close();

        // Parsear JSON
        JSONObject jsonObject = new JSONObject(jsonResponse.toString());

        // Extraer campos específicos
        String nombre = jsonObject.getString("name");
        String email = jsonObject.getString("email");
        String foto = jsonObject.getString("picture");

        // Puedes crear un objeto o retornar la información que necesites
        System.out.println("Nombre: " + nombre);
        System.out.println("Email: " + email);
        System.out.println("Foto: " + foto);

        return jsonObject;
    }

    public void procesarCallback() throws IOException {
        Map<String,String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        String code = params.get("code");
        String estadoRecibido = params.get("state");
        String estadoSes = (String) FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap().get("state");
        if (!estadoSes.equals(estadoRecibido)) {
            System.out.println("Error de CSRF: rechazar autenticación"); // Error de CSRF: rechazar autenticación
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml?faces-redirect=true");
        } else {
            String access_token = reclamarToken(code, System.getenv("CLIENT_ID"), System.getenv("CLIENT_SECRET"), System.getenv("REDIRECT_URI"));
            JSONObject userInfo = obtenerUserInfo(access_token);

            User user = new User();
            user.setGoogleId(userInfo.getString("sub"));
            user.setNombre(userInfo.getString("name"));
            user.setEmail(userInfo.getString("email"));
            user.setImageUrl(userInfo.getString("picture"));

            userBean.setAccessToken(access_token);
            userBean.setLoggedUser(user);

            FacesContext.getCurrentInstance().getExternalContext().redirect("perfil.xhtml?faces-redirect=true");
        }
    }

    private String reclamarToken(String code, String clientId, String clientSecret, String redirectUri) throws IOException {
        // Crear la URL de la solicitud
        URL url = new URL("https://oauth2.googleapis.com/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        // Crear los datos de la solicitud
        String data = "code=" + URLEncoder.encode(code, StandardCharsets.UTF_8)
                + "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8)
                + "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8)
                + "&redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8)
                + "&grant_type=authorization_code";

        // Enviar los datos en el cuerpo de la solicitud
        conn.getOutputStream().write(data.getBytes(StandardCharsets.UTF_8));

        // Leer la respuesta
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        // Leer la respuesta línea por línea
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Convertir la respuesta a un objeto JSON
        JSONObject jsonResponse = new JSONObject(response.toString());

        // Retornar el token (por ejemplo, si el JSON contiene "access_token")
        return jsonResponse.getString("access_token");
    }
}
