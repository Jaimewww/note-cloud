package edu.cc.notecloud.view;

import edu.cc.notecloud.entity.Note;
import edu.cc.notecloud.services.NoteService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class NoteListBean implements Serializable {
    private Long asignatureId;
    private List<Note> notes;

    @Inject
    NoteService noteService;

    @PostConstruct
    public void init() {
        String asignatureIdParam = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("asignatureId");

        if (asignatureIdParam != null) {
            try {
                asignatureId = Long.parseLong(asignatureIdParam);
                notes = noteService.findByAsignatureId(asignatureId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public String goToDetail(Long noteId) {
        return "detalle-apunte.xhtml?faces-redirect=true&noteId=" + noteId;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public Long getAsignatureId() {
        return asignatureId;
    }

    public void setAsignatureId(Long asignatureId) {
        this.asignatureId = asignatureId;
    }
}