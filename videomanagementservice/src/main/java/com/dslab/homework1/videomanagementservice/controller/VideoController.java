package com.dslab.homework1.videomanagementservice.controller;

import com.dslab.homework1.videomanagementservice.entity.Video;
import com.dslab.homework1.videomanagementservice.entity.VideoRepository;
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
import java.util.Optional;

@Controller
@RequestMapping(path = "/videos")
public class VideoController {

    @Autowired
    VmsVideoService videoService;

    //POST http://localhost:8080/videos/
    @PostMapping(path = "")
    public @ResponseBody Video addVideo (Authentication auth,@RequestBody Video video){
        return videoService.addVideo(auth.getName(),video);
    }

    //GET http://localhost:8080/videos
    @GetMapping(path="")
    public @ResponseBody Iterable<Video> getVideos (){
        return videoService.getAllVideos();
    }


    //POST http://localhost:8080/videos/id
    @PostMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> UploadVideo(Authentication auth, @PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {


        //Prende in ingresso un file video.mp4
        File convertFile = new File("/home/michelegrasso/Scrivania/" + file.getOriginalFilename());
        convertFile.createNewFile();

        try (FileOutputStream fout = new FileOutputStream(convertFile)){
            fout.write(file.getBytes());
        }
        catch (Exception exe)
        {
            exe.printStackTrace();
        }


        if (videoService.VideoCheck(auth.getName(),id)){
            //Salva il video sullo storage al path /var/video/id/video.mp4



            //Manda una richiesta REST POST al vps


            return new ResponseEntity<>("Video Uploaded", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

}