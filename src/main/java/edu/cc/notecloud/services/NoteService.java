package edu.cc.notecloud.services;

import edu.cc.notecloud.dto.NoteDTO;
import edu.cc.notecloud.entity.Note;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class NoteService {
    @Inject
    private NoteRepository noteRepository;

    @Inject
    private AsignatureRepository asignatureRepository;

    @Inject
    private UserRepository userRepository;

    public List<Note> findByAsignatureId(Long asignatureId) {
        return noteRepository.findByAsignatureId(asignatureId);
    }

    public Note findById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }

    public void createNote(NoteDTO dto) {
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setPdfPath(dto.getPdfPath());
        note.setCreatedAt(LocalDateTime.now());
        note.setState(true);
        note.setAsignature(asignatureRepository.findById(dto.getAsignatureId()).orElse(null));
        note.setUser(userRepository.findById(dto.getUserId()).orElse(null));
        noteRepository.save(note);
    }
}