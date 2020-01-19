package com.dslab.homework1.videomanagementservice.controller;

import com.dslab.homework1.videomanagementservice.entity.Video;
import com.dslab.homework1.videomanagementservice.service.VmsVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping(path = "/videos")
public class VideoController {

    @Autowired
    VmsVideoService videoService;


    //1. POST http://localhost:8080/videos/
    @PostMapping(path = "")
    public @ResponseBody Video addVideo (Authentication auth,@RequestBody Video video){
        return videoService.addVideo(auth.getName(),video);
    }


    //2. POST http://localhost:8080/videos/id
    @PostMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadVideo(Authentication auth, @PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {

        if(videoService.encodeVideo(auth.getName(), id, file)) {
            return new ResponseEntity<>("Video Uploaded", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }


    //3. GET http://localhost:8080/videos
    @GetMapping(path="")
    public @ResponseBody Iterable<Video> getVideos (){
        return videoService.getAllVideos();
    }


    //4. GET http://localhost:8080/videos/id
    @GetMapping(path = "/{id}")
    public ResponseEntity<String> getVideo (@PathVariable Integer id) {

        if (videoService.VideoCheckGET(id)){
            return new ResponseEntity<>("/videofiles/"+id+"/video.mpd", HttpStatus.MOVED_PERMANENTLY);
        }else{
            return new ResponseEntity<>("Video doesn't exist!", HttpStatus.NOT_FOUND);
        }
    }

}