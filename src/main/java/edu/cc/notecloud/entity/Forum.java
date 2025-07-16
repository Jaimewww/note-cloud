package edu.cc.notecloud.entity;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="forums")
@DiscriminatorValue("forum")
public class Forum extends Post {
    @Column(name = "comments_count",nullable = false)
    private Integer commentsCount;

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }
}
