package edu.cc.notecloud.dto;

import jakarta.validation.constraints.NotBlank;

public class NoteDTO {
    @NotBlank(message = "ERROR: El título de la nota es obligatorio")
    private String title;

}
