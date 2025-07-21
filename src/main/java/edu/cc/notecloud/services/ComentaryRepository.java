package edu.cc.notecloud.services;

import edu.cc.notecloud.dto.ComentaryDTO;
import edu.cc.notecloud.entity.Comentary;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class ComentaryRepository {

    @Inject
    CrudGenericService crudService;

    @Inject
    private NoteRepository noteRepository;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Comentary save(@NotNull Comentary comentary) {
        return crudService.create(comentary);
    }

    public List<Comentary> findByNoteId(@NotNull Long noteId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", noteId);
        return crudService.findWithQuery(
                "SELECT c FROM Comentary c WHERE c.note.id = :id",
                params
        );
    }

    public void save(Long noteId, ComentaryDTO dto) {
        Comentary comment = new Comentary();
        comment.setTitle(dto.getTitle());
        comment.setContent(dto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setState(true);
        comment.setNote(noteRepository.findById(noteId));
        save(comment);
    }
}