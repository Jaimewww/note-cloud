package edu.cc.notecloud.view;

import edu.cc.notecloud.dto.AsignatureDTO;
import edu.cc.notecloud.entity.Asignature;
import edu.cc.notecloud.services.AsignatureService;
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
    AsignatureService asignatureService;

    @PostConstruct
    public void init() {
        asignatures = asignatureService.findAll();
        newAsignature = new AsignatureDTO();
    }

    public String goToNotes(Long asignatureId) {
        return "apuntes.xhtml?faces-redirect=true&asignatureId=" + asignatureId;
    }
}

