package edu.cc.notecloud.view;

import edu.cc.notecloud.dto.ComentaryDTO;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@SessionScoped
public class CommentBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private ComentaryDTO comment = new ComentaryDTO();

    public ComentaryDTO getComment() {
        return comment;
    }

    public void setComment(ComentaryDTO comment) {
        this.comment = comment;
    }

    public void guardar() {
        System.out.println("Guardar comentario: " + comment.getContent());
    }

    public void editar() {
        System.out.println("Editar comentario: " + comment.getContent());
    }

    public void eliminar() {
        System.out.println("Eliminar comentario: " + comment.getContent());
    }

    public void listar() {
        System.out.println("Listar comentarios");
    }
}
