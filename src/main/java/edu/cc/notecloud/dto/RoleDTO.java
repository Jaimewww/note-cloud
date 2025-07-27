package edu.cc.notecloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RoleDTO {
    @NotBlank(message = "ERROR: El nombre del rol es obligatorio")
    @Size(max = 30, message = "ERROR: El nombre del rol no puede exceder los 30 caracteres")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
