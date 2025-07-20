package edu.cc.notecloud.services;

import edu.cc.notecloud.dto.ComentaryDTO;
import edu.cc.notecloud.entity.Comentary;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ComentaryService {
    @Inject
    private ComentaryRepository comentaryRepository;

    @Inject
    private NoteRepository noteRepository;

    public List<Comentary> findByNoteId(Long noteId) {
        return comentaryRepository.findByNoteId(noteId);
    }

    public void save(Long noteId, ComentaryDTO dto) {
        Comentary comment = new Comentary();
        comment.setTitle(dto.getTitle());
        comment.setContent(dto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setState(true);
        comment.setNote(noteRepository.findById(noteId).orElse(null));
        comentaryRepository.save(comment);
    }
}