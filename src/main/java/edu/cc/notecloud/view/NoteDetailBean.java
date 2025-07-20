package edu.cc.notecloud.view;

import edu.cc.notecloud.dto.ComentaryDTO;
import edu.cc.notecloud.entity.Comentary;
import edu.cc.notecloud.entity.Note;
import edu.cc.notecloud.services.ComentaryService;
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
public class NoteDetailBean implements Serializable {

    private Long noteId;
    private Note note;

    @Inject
    private NoteService noteService;

    @PostConstruct
    public void init() {
        String noteIdParam = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("noteId");

        if (noteIdParam != null) {
            try {
                noteId = Long.parseLong(noteIdParam);
                note = noteService.findById(noteId);
            } catch (NumberFormatException e) {
                // manejar error de conversión
                e.printStackTrace();
            }
        }
    }

    // getters y setters
    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }
}