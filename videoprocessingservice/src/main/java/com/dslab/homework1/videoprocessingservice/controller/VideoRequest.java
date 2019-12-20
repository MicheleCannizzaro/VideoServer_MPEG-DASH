package com.dslab.homework1.videoprocessingservice.controller;


import javax.validation.constraints.NotNull;

public class VideoRequest {

    @NotNull
    private Integer videoId;

    public Integer getVideoId() {
        return videoId;
    }

    public VideoRequest setVideoId(Integer videoId) {
        this.videoId = videoId;
        return this;
    }
}
