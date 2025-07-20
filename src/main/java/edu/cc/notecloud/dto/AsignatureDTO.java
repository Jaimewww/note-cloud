package edu.cc.notecloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AsignatureDTO {
    @NotBlank(message = "ERROR: El nombre de la asignatura es obligatorio")
    @Size(max = 60, message = "ERROR: El nombre de la asignatura no puede exceder los 60 caracteres")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

