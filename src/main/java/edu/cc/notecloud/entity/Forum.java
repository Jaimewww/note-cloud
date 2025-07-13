package edu.cc.notecloud.entity;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FORUM")
public class Forum extends Post {
    @Column(nullable = false)
    private Integer commentsCount;
}
