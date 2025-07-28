package edu.cc.notecloud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NoteDTO {
    @NotBlank(message = "ERROR: El título es obligatorio")
    @Size(max = 100, message = "ERROR: El título no puede exceder los 100 caracteres")
    private String title;

    @Size(max = 1000, message = "ERROR: La descripción no puede exceder los 1000 caracteres")
    @NotBlank(message = "ERROR: El contenido de la nota es obligatorio")
    private String content;

    @NotBlank(message = "ERROR: Se requiere una ruta válida para el PDF")
    private String pdfPath;

    @NotNull(message = "ERROR: La asignatura es obligatoria")
    private Long asignatureId;

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

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public Long getAsignatureId() {
        return asignatureId;
    }

    public void setAsignatureId(Long asignatureId) {
        this.asignatureId = asignatureId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
