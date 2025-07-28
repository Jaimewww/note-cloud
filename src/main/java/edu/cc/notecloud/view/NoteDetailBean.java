package edu.cc.notecloud.view;

import edu.cc.notecloud.dto.ComentaryDTO;
import edu.cc.notecloud.entity.Comentary;
import edu.cc.notecloud.entity.Forum;
import edu.cc.notecloud.entity.Note;
import edu.cc.notecloud.services.ComentaryRepository;
import edu.cc.notecloud.services.ForumRepository;
import edu.cc.notecloud.services.NoteRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class NoteDetailBean implements Serializable {

    private Long noteId;
    private Note note;
    private Long forumId;
    private Forum forum;
    private List<Comentary> comments;
    private ComentaryDTO newComment = new ComentaryDTO();

    @Inject
    private NoteRepository noteRepository;

    @Inject
    private ForumRepository forumRepository;

    @Inject
    private ComentaryRepository comentaryRepository;

    @Inject
    private UserBean userBean;

    @PostConstruct
    public void init() {
        String noteIdParam = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("noteId");

        String forumIdParam = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap()
                .get("forumId");

        if (noteIdParam != null) {
            try {
                noteId = Long.parseLong(noteIdParam);
                note = noteRepository.findById(noteId);
                comments = comentaryRepository.findByNoteId(noteId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        if (forumIdParam != null) {
            try {
                forumId = Long.parseLong(forumIdParam);
                forum = forumRepository.findById(forumId);
                comments = comentaryRepository.findByForumId(forumId);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void addComment() {
        Long userId = userBean.getLoggedUser().getId();
        newComment.setUserId(userId);
        Long id = forumId != null ? forumId : noteId; // usa forumId si está disponible, sino noteId
        comentaryRepository.save(id, newComment);
        newComment = new ComentaryDTO(); // limpia el formulario
        comments = comentaryRepository.findByNoteId(forumId); // recarga comentarios
    }

    public boolean isNoteComment() {
        return noteId != null;
    }



    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public List<Comentary> getComments() {
        return comments;
    }

    public void setComments(List<Comentary> comments) {
        this.comments = comments;
    }

    public ComentaryDTO getNewComment() {
        return newComment;
    }

    public void setNewComment(ComentaryDTO newComment) {
        this.newComment = newComment;
    }

    public NoteRepository getNoteRepository() {
        return noteRepository;
    }

    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public ComentaryRepository getComentaryRepository() {
        return comentaryRepository;
    }

    public void setComentaryRepository(ComentaryRepository comentaryRepository) {
        this.comentaryRepository = comentaryRepository;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }
}