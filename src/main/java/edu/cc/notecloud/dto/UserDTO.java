package edu.cc.notecloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

public class UserDTO {
    @NotBlank(message = "ERROR: El correo es obligatorio")
    @Email(message = "ERROR: Formato de correo inválido")
    @Size(max = 60, message = "ERROR: El correo no puede exceder los 60 caracteres")
    private String email;

    @NotBlank(message = "ERROR: El nombre es obligatorio")
    @Size(max = 40, message = "ERROR: El nombre no puede exceder los 40 caracteres")
    private String name;

    @NotNull(message = "ERROR: La contraseña es obligatoria")
    @Size(min = 8, message = "ERROR: La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank(message = "ERROR: La confirmación de contraseña es obligatoria")
    private String confirmPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
