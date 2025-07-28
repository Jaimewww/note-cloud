package edu.cc.notecloud.entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="forums")
@DiscriminatorValue("forum")
public class Forum extends Post {
    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL)
    private List<Comentary> comments;

    @Column(name = "comments_count",nullable = false)
    private Integer commentsCount;

    public Integer getCommentsCount() {
        return comments.size();
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    @OneToMany(mappedBy = "forum", cascade = CascadeType.ALL)
    private List<Comentary> comentaries;
}
