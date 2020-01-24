package com.dslab.homework1.videoprocessingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VideoprocessingserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VideoprocessingserviceApplication.class, args);
    }

}
