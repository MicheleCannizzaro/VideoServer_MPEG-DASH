package com.dslab.homework1.videomanagementservice.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "The video name parameter must not be blank!")
    private String video_name;

    @NotNull(message = "The author name parameter must not be blank!")
    private String author_name;

    @NotNull(message = "The state parameter must not be blank!")
    private String state;

    @ManyToOne
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVideo_name() {
        return video_name;
    }

    public Video setVideo_name(String video_name) {
        this.video_name = video_name;
        return this;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public Video setAuthor_name(String author_name) {
        this.author_name = author_name;
        return this;
    }

    public String getState() {
        return state;
    }

    public Video setState(String state) {
        this.state = state;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Video setUser(User user) {
        this.user = user;
        return this;
    }
}
