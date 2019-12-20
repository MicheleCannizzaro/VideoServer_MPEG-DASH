package com.dslab.homework1.videoprocessingservice.controller;

import com.dslab.homework1.videoprocessingservice.service.VideoProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/videos")
public class VideoProcessController{

    @Autowired
    VideoProcessService service;

    //COMPLETARE
    /*//POST http://localhost:8080/videos/process
    @PostMapping(path = "/process")
    public ResponseEntity<String> process (@RequestBody VideoRequest video_request){

            //Sistemare!!!

        if(service.encoding()){

        }
    }*/
}
