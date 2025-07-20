package edu.cc.notecloud.services;

import edu.cc.notecloud.dto.AsignatureDTO;
import edu.cc.notecloud.entity.Asignature;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class AsignatureService {
    @Inject
    private AsignatureRepository asignatureRepository;

    public List<Asignature> findAll() {
        return AsignatureRepository.findAll();
    }

    public void create(AsignatureDTO dto) {
        Asignature asignature = new Asignature();
        asignature.setName(dto.getName());
        asignatureRepository.save(asignature);
    }
}