package edu.cc.notecloud.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// Note.java
@Entity
@Table(name = "notes")
@DiscriminatorValue("note")
public class Note extends Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pdf_path", nullable = false)
    private String pdfPath;

    @ManyToOne(optional = false)
    @JoinColumn(name = "asignature_id")
    private Asignature asignature;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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
