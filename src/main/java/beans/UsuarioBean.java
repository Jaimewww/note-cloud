package beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import model.Usuario;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

@Named
@SessionScoped
public class UsuarioBean implements Serializable {
    private Usuario usuarioLogueado;
    private String accessToken;

    public String logout() {
        String url = "https://oauth2.googleapis.com/revoke?token=" + accessToken;

        try {
            // Realizar la solicitud de revocación
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                System.out.println("Token revocado exitosamente.");
            } else {
                System.out.println("Error al revocar el token.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Invalida la sesión en la aplicación
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login.xhtml?faces-redirect=true";
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
