package edu.cc.notecloud.view;

import edu.cc.notecloud.dto.AsignatureDTO;
import edu.cc.notecloud.entity.Asignature;
import edu.cc.notecloud.services.AsignatureRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AsignatureBean implements Serializable {
    private List<Asignature> asignatures;
    private AsignatureDTO newAsignature;

    @Inject
    AsignatureRepository asignatureRepository;

    @PostConstruct
    public void init() {
        asignatures = asignatureRepository.findAll();
        newAsignature = new AsignatureDTO();
    }

    public void create() {
        asignatureRepository.create(newAsignature);
        newAsignature = new AsignatureDTO(); // Limpia el formulario
        asignatures = asignatureRepository.findAll(); // Refresca la tabla
    }

    public String goToNotes(Long asignatureId) {
        return "apuntes1.xhtml?faces-redirect=true&asignatureId=" + asignatureId;
    }

    public List<Asignature> getAsignatures() {
        return asignatures;
    }

    public void setAsignatures(List<Asignature> asignatures) {
        this.asignatures = asignatures;
    }

    public AsignatureDTO getNewAsignature() {
        return newAsignature;
    }

    public void setNewAsignature(AsignatureDTO newAsignature) {
        this.newAsignature = newAsignature;
    }

    public AsignatureRepository getAsignatureRepository() {
        return asignatureRepository;
    }

    public void setAsignatureRepository(AsignatureRepository asignatureRepository) {
        this.asignatureRepository = asignatureRepository;
    }
}

