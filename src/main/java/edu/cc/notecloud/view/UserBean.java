package edu.cc.notecloud.view;

import edu.cc.notecloud.business.SecurityFacade;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import edu.cc.notecloud.entity.User;

import java.io.IOException;
import java.io.Serializable;
@Named
@SessionScoped
public class UserBean implements Serializable {
    private User loggedUser;
    private String accessToken;

    @Inject
    SecurityFacade securityFacade;

    public String logout() {
        try {
            securityFacade.revokeGoogleToken(accessToken);
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new jakarta.faces.application.FacesMessage(
                            jakarta.faces.application.FacesMessage.SEVERITY_ERROR,
                            "Error al cerrar sesión",
                            ex.getMessage()));
            ex.printStackTrace();
            return null;
        }

        // Limpieza explícita (opcional pero recomendable)
        this.loggedUser = null;
        this.accessToken = null;

        FacesContext.getCurrentInstance()
                .getExternalContext()
                .invalidateSession();

        return "login.xhtml?faces-redirect=true";
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) { this.loggedUser = loggedUser; }

    public String getAccessToken() { return accessToken; }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
