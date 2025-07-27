package edu.cc.notecloud.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public class TaskDTO {
    @NotBlank(message = "ERROR: El título es obligatorio")
    @Size(min = 1, max = 50, message = "ERROR: El título debe tener entre 1 y 50 caracteres")
    private String title;

    @NotBlank(message = "ERROR: La descripción es obligatoria")
    @Size(max = 200, message = "ERROR: La descripción no puede exceder los 200 caracteres")
    private String description;

    @FutureOrPresent(message = "La fecha de vencimiento debe ser hoy o en el futuro")
    private Date dueDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
