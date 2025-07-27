package edu.cc.notecloud.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// Note.java
@Entity
@Table(name = "notes")
@DiscriminatorValue("note")
public class Note extends Post {

    @Column(name = "pdf_path", nullable = false)
    private String pdfPath;

    @ManyToOne(optional = false)
    @JoinColumn(name = "asignature_id")
    private Asignature asignature;

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public Asignature getAsignature() {
        return asignature;
    }

    public void setAsignature(Asignature asignature) {
        this.asignature = asignature;
    }
}
