package edu.cc.notecloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ComentaryDTO {
    @NotBlank(message = "ERROR: El título del comentario es obligatorio")
    @Size(max = 100, message = "ERROR: El título no puede exceder los 100 caracteres")
    private String title;

    @NotBlank(message = "ERROR: El contenido del comentario no puede estar vacío")
    @Size(max = 1000, message = "ERROR: El contenido no puede exceder los 1000 caracteres")
    private String content;

    @NotNull(message = "ERROR: El foro es obligatorio")
    private Long forumId;

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

    public Long getForumId() {
        return forumId;
    }

    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
