package edu.cc.notecloud.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="posts")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name = "post_type", length = 20)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title" ,nullable = false)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @Column(name="state" ,nullable = false)
    private boolean state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
