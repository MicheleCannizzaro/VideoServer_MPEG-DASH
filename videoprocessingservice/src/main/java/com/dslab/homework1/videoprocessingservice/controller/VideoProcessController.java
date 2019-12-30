package com.dslab.homework1.videoprocessingservice.controller;

import com.dslab.homework1.videoprocessingservice.service.VideoProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/videos")
public class VideoProcessController{

    @Autowired
    VideoProcessService service;

    //POST http://localhost:8080/videos/process
    @PostMapping(path = "/process")
    public ResponseEntity<String> process (@RequestBody VideoRequest videoRequest){

        if(service.encode(videoRequest.getVideoId())){
            return new ResponseEntity<>("Video encoded and saved", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Video not encoded", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
