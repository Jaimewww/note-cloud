package edu.cc.notecloud.services;

import edu.cc.notecloud.entity.Note;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class NoteRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Note> findByAsignatureId(Long asignatureId) {
        return em.createQuery("SELECT n FROM Note n WHERE n.asignature.id = :id", Note.class)
                .setParameter("id", asignatureId)
                .getResultList();
    }

    public Optional<Note> findById(Long id) {
        return Optional.ofNullable(em.find(Note.class, id));
    }

    public void save(Note note) {
        em.persist(note);
    }
}