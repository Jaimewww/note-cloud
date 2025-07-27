package edu.cc.notecloud.view;

import edu.cc.notecloud.dto.NoteDTO;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class NoteBean implements Serializable {

    private NoteDTO note = new NoteDTO();

    public NoteDTO getNote() {
        return note;
    }

    public void setNote(NoteDTO note) {
        this.note = note;
    }

    public void guardar() {
        System.out.println("Guardar nota: " + note.getTitle());
        // Aquí luego irá la lógica real o llamada al Facade
    }

    public void editar() {
        System.out.println("Editar nota: " + note.getTitle());
    }

    public void eliminar() {
        System.out.println("Eliminar nota: " + note.getTitle());
    }

    public void listar() {
        System.out.println("Listar notas");
    }
}
