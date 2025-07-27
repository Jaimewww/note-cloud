package edu.cc.notecloud.services;

// AsignatureRepository.java
import edu.cc.notecloud.dto.AsignatureDTO;
import edu.cc.notecloud.entity.Asignature;
import jakarta.ejb.Stateless;

import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class AsignatureRepository {

    @Inject
    CrudGenericService crud;

    public Asignature findById(Long id) {
        return crud.find(Asignature.class, id);
    }

    public List<Asignature> findAll() {
        return crud.findWithQuery("SELECT a FROM Asignature a");
    }

    public Asignature save(Asignature a) {
        return crud.create(a);
    }

    public void create(AsignatureDTO dto) {
        Asignature asignature = new Asignature();
        asignature.setName(dto.getName());
        save(asignature);
    }
}
