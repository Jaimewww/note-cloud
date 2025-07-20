package edu.cc.notecloud.services;

import edu.cc.notecloud.entity.Comentary;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class ComentaryRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Comentary> findByNoteId(Long noteId) {
        return em.createQuery("SELECT c FROM Comentary c WHERE c.note.id = :id", Comentary.class)
                .setParameter("id", noteId)
                .getResultList();
    }

    public void save(Comentary comentary) {
        em.persist(comentary);
    }
}