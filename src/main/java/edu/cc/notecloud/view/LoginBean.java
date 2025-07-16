package edu.cc.notecloud.view;

import edu.cc.notecloud.business.SecurityFacade;
import edu.cc.notecloud.dto.UserDTO;
import edu.cc.notecloud.entity.User;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;

import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    UserBean userBean;

    @Inject
    SecurityFacade securityFacade;

    @Valid
    private UserDTO userDTO = new UserDTO();

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public String login() {
        char[] password = userDTO.getPassword().toCharArray();
        try {
            User user = securityFacade.authenticate(userDTO.getEmail(), password);
            if (user != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
                userBean.setLoggedUser(user);
                return "perfil.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Credenciales inválidas"));
                return null;
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error inesperado al iniciar sesión"));
            return null;
        } finally {
            java.util.Arrays.fill(password, '\0');
        }
    }
}
