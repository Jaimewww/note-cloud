package edu.cc.notecloud.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


// Comentary.java
@Entity
@Table(name = "comentarys")
public class Comentary extends Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Forum forum;

    @ManyToOne
    private Note note;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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