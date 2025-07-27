package edu.cc.notecloud.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


// Comentary.java
@Entity
@Table(name = "comentarys")
public class Comentary extends Post {

    @ManyToOne
    private Forum forum;

    @ManyToOne
    private Note note;

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }


}