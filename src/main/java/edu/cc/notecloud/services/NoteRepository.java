package edu.cc.notecloud.services;

import edu.cc.notecloud.dto.NoteDTO;
import edu.cc.notecloud.entity.Note;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Stateless
public class NoteRepository {

    @Inject
    private AsignatureRepository asignatureRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    CrudGenericService crudService;

    public List<Note> findByAsignatureId(@NotNull Long asignatureId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", asignatureId);

        return crudService.findWithQuery(
                "SELECT n FROM Note n WHERE n.asignature.id = :id",
                params
        );
    }

    public Note findById(Long id) {
        return crudService.find(Note.class, id);
    }



    public Note save(Note note) {
        if (note.getId() == null) {
            return crudService.create(note);
        } else {
            return crudService.update(note);
        }
    }

    public void createNote(NoteDTO dto) {
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setContent(dto.getContent());
        note.setPdfPath(dto.getPdfPath());
        note.setCreatedAt(LocalDateTime.now());
        note.setState(true);
        note.setAsignature(asignatureRepository.findById(dto.getAsignatureId()));
        note.setUser(userRepository.findById(dto.getUserId()).orElse(null));
        save(note);
    }
}