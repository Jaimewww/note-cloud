package edu.cc.notecloud.view;

import edu.cc.notecloud.dto.ForumDTO;
import edu.cc.notecloud.entity.Forum;
import edu.cc.notecloud.services.ForumRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ForumBean implements Serializable {

    private List<Forum> forums;

    @Valid
    private ForumDTO newForum = new ForumDTO();

    @Inject
    private ForumRepository forumRepository;

    @Inject
    private UserBean userBean;

    @PostConstruct
    public void init() {
        forums = forumRepository.findAll();
    }

    public void createForum() throws EntityNotFoundException {
        Long userId = userBean.getLoggedUser().getId();
        newForum.setUserId(userId);

        forumRepository.createForum(newForum);
        newForum = new ForumDTO();
        forums = forumRepository.findAll(); // recargar lista
    }

    public String goToDetail(Long forumId) {
        return "detalle-apunte.xhtml?faces-redirect=true&forumId=" + forumId;
    }

    // Getters y Setters
    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    public ForumDTO getNewForum() {
        return newForum;
    }

    public void setNewForum(ForumDTO newForum) {
        this.newForum = newForum;
    }
}