package edu.cc.notecloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ForumDTO {
    @NotBlank(message = "ERROR: El título del foro es obligatorio")
    @Size(max = 100, message = "ERROR: El título no puede exceder los 100 caracteres")
    private String title;

    @NotBlank(message = "ERROR: La descripción del foro es obligatoria")
    @Size(max = 2000, message = "ERROR: La descripción no puede exceder los 2000 caracteres")
    private String content;

    @NotNull(message = "ERROR: El usuario es obligatorio")
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
