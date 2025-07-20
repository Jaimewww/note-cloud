package edu.cc.notecloud.view;

import edu.cc.notecloud.business.SecurityFacade;
import edu.cc.notecloud.dto.UserDTO;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.validation.Valid;

import java.io.Serializable;
import java.util.Arrays;

@Named
@SessionScoped
public class RegisterBean implements Serializable {
    @Valid
    private UserDTO userDTO = new UserDTO();

    public UserDTO getUserDTO() { return userDTO; }

    public String register() {
        if(SecurityFacade.findUserByEmail(userDTO.getEmail()) != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "ERROR: El correo ya está registrado", ""));
            return null;
        }
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "ERROR: Las contraseñas no coinciden", ""));
            return null;
        }
        char[] raw = userDTO.getPassword().toCharArray();
        try {
            /* delega todo al facade */
            SecurityFacade.createUser(
                    userDTO.getName(),
                    userDTO.getEmail(),
                    raw);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Usuario registrado correctamente", ""));
            return null;

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", "Ocurrió un error al registrar el usuario"));
            return null;
        } finally {
            Arrays.fill(raw, '\0');
        }
    }
}
