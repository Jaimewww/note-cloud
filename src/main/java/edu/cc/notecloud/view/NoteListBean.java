package edu.cc.notecloud.view;

import edu.cc.notecloud.dto.NoteDTO;
import edu.cc.notecloud.entity.Note;
import edu.cc.notecloud.services.NoteRepository;
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
    private String asignatureName;
    private List<Note> notes;
    private NoteDTO newNote = new NoteDTO();

    @Inject
    NoteRepository noteRepository;

    @PostConstruct
    public void init() {
        String asignatureIdParam = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("asignatureId");

        if (asignatureIdParam != null) {
            try {
                asignatureId = Long.parseLong(asignatureIdParam);
                notes = noteRepository.findByAsignatureId(asignatureId);
                newNote.setAsignatureId(asignatureId); // asignar para el create
                asignatureName = notes.isEmpty() ? "Asignatura sin nombre" : notes.get(0).getAsignature().getName();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void createNote() {
        // Simulación del ID del usuario logueado (en producción lo sacas del contexto)
        Long fakeUserId = 1L;
        newNote.setUserId(fakeUserId);

        noteRepository.createNote(newNote);
        newNote = new NoteDTO();
        newNote.setAsignatureId(asignatureId); // mantener el ID
        notes = noteRepository.findByAsignatureId(asignatureId); // recargar tabla
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

    public String getAsignatureName() {
        return asignatureName;
    }

    public void setAsignatureName(String asignatureName) {
        this.asignatureName = asignatureName;
    }

    public NoteDTO getNewNote() {
        return newNote;
    }

    public void setNewNote(NoteDTO newNote) {
        this.newNote = newNote;
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }

    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }
}