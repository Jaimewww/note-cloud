package edu.cc.notecloud.services;

// AsignatureRepository.java
import edu.cc.notecloud.entity.Asignature;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class AsignatureRepository {

    @PersistenceContext
    private EntityManager em;

    public static List<Asignature> findAll() {
        return em.createQuery("SELECT a FROM Asignature a", Asignature.class).getResultList();
    }

    public void save(Asignature asignature) {
        em.persist(asignature);
    }

    public Asignature findById(Long id) {
        return em.find(Asignature.class, id);
    }
}