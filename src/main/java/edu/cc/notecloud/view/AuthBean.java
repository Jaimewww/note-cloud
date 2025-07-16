package edu.cc.notecloud.view;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.*;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import edu.cc.notecloud.business.SecurityFacade;
import edu.cc.notecloud.entity.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.shaded.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Named
@SessionScoped
public class AuthBean implements Serializable {

    @Inject
    UserBean userBean;

    @Inject
    SecurityFacade securityFacade;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String USER_INFO_URL = "https://openidconnect.googleapis.com/v1/userinfo";

    private final String clientID = "655973556202-1bvbmcmkbqhr06vck4n42hk01fktjali.apps.googleusercontent.com";
    private final String clientSecret = "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";
    private final String redirectUri = "http://localhost:9080/callback.xhtml";

    public void googleLogin() throws IOException {
        String state = UUID.randomUUID().toString();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("state", state);

        String redirect = new AuthorizationCodeRequestUrl(
                "https://accounts.google.com/o/oauth2/v2/auth", clientID)
                .setRedirectUri(redirectUri)
                .setScopes(Collections.singletonList("openid email profile"))
                .setState(state)
                .build();
        FacesContext.getCurrentInstance().getExternalContext().redirect(redirect);
    }

    public void procesarCallback() throws IOException {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String code = params.get("code");
        String estadoRecibido = params.get("state");
        String estadoSesion = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("state");

        if (!estadoRecibido.equals(estadoSesion)) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml?faces-redirect=true");
            return;
        }

        try {
            Credential credential = intercambiarCodigoPorToken(code);
            JSONObject userInfo = obtenerUserInfo(credential);

            User googleUser = securityFacade.loginGoogleUser(
                    userInfo.getString("name"), userInfo.getString("email"));

            userBean.setAccessToken(credential.getAccessToken());
            userBean.setLoggedUser(googleUser);

            FacesContext.getCurrentInstance().getExternalContext().redirect("perfil.xhtml?faces-redirect=true");
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml?error");
        }
    }

    private Credential intercambiarCodigoPorToken(String code) throws IOException, GeneralSecurityException {
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();

        TokenResponse tokenResponse = new AuthorizationCodeTokenRequest(
                transport, JSON_FACTORY,
                new GenericUrl("https://oauth2.googleapis.com/token"), code)
                .setRedirectUri(redirectUri)
                .setClientAuthentication(new com.google.api.client.auth.oauth2.ClientParametersAuthentication(clientID, clientSecret))
                .execute();

        return new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
                .setTransport(transport)
                .setJsonFactory(JSON_FACTORY)
                .setClientAuthentication(new com.google.api.client.auth.oauth2.ClientParametersAuthentication(clientID, clientSecret))
                .setTokenServerEncodedUrl("https://oauth2.googleapis.com/token")
                .build()
                .setFromTokenResponse(tokenResponse);
    }

    private JSONObject obtenerUserInfo(Credential credential) throws IOException, GeneralSecurityException {
        HttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        HttpRequestFactory requestFactory = transport.createRequestFactory(credential);

        HttpRequest request = requestFactory.buildGetRequest(new com.google.api.client.http.GenericUrl(USER_INFO_URL));
        HttpResponse response = request.execute();

        try (InputStreamReader reader = new InputStreamReader(response.getContent())) {
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
            return new JSONObject(sb.toString());
        }
    }
}