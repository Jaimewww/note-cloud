package edu.cc.notecloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentDTO {
    @NotBlank(message = "ERROR: El comentario no puede estar vacío")
    private String content;

    @NotNull(message = "ERROR: Se requiere el ID de la nota para comentar")
    private Long noteId;

    @NotNull(message = "ERROR: Se requiere el ID del usuario que comenta")
    private Long userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
