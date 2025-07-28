package edu.cc.notecloud.view;

import edu.cc.notecloud.business.SecurityFacade;
import edu.cc.notecloud.dto.UserDTO;
import edu.cc.notecloud.entity.User;
import edu.cc.notecloud.security.Permission;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    @Inject
    private UserBean userBean;

    @Inject
    private SecurityFacade securityFacade;

    @Valid
    private UserDTO userDTO = new UserDTO();

    private List<Permission> permissions; // <- permisos del usuario en sesión

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String resource, String action) {
        try {
            return permissions.stream()
                    .anyMatch(p -> p.matchWith(resource, Enum.valueOf(edu.cc.notecloud.security.ActionType.class, action)));
        } catch (Exception e) {
            return false;
        }
    }

    public String login() {
        char[] password = userDTO.getPassword().toCharArray();
        try {
            User user = securityFacade.authenticate(userDTO.getEmail(), password);
            if (user != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
                userBean.setLoggedUser(user);

                // Aquí se cargan los permisos
                this.permissions = securityFacade.getPermissionsOf(user);

                return "apuntes.xhtml?faces-redirect=true";
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

    public boolean isAdmin() {
        User user = userBean.getLoggedUser();
        return securityFacade.isAdmin(user);
    }

    public boolean isUser() {
        User user = userBean.getLoggedUser();
        return securityFacade.isUser(user);
    }
}
