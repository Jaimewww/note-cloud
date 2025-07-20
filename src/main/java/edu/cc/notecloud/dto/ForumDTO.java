package edu.cc.notecloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ForumDTO {
    @NotBlank(message = "ERROR: El nombre del foro es obligatorio")
    private String name;

    @NotBlank(message = "ERROR: La descripción del foro es obligatoria")
    private String description;

    @NotNull(message = "ERROR: Se requiere el ID del usuario creador")
    private Long createdByUserId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }
}
