package edu.cc.notecloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NoteDTO {
    @NotBlank(message = "ERROR: El título de la nota es obligatorio")
    @Size(max = 100, message = "ERROR: El título no puede tener más de 100 caracteres")
    private String title;

    @NotBlank(message = "ERROR: El contenido de la nota es obligatorio")
    private String content;

    @NotNull (message = "ERROR: Se requiere el ID del usuario que crea la nota")
    private Long userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
