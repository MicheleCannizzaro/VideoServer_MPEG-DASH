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

    //1. POST http://localhost:8080/videos/
    @PostMapping(path = "")
    public @ResponseBody Video addVideo (Authentication auth,@RequestBody Video video){
        return videoService.addVideo(auth.getName(),video);
    }

    //3. GET http://localhost:8080/videos
    @GetMapping(path="")
    public @ResponseBody Iterable<Video> getVideos (){
        return videoService.getAllVideos();
    }


    //2. POST http://localhost:8080/videos/id
    @PostMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadVideo(Authentication auth, @PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {

         if (videoService.VideoCheckPOST(auth.getName(),id)){
             //Take a file video.mp4 as input
             //Save video.mp4 into the storage on path /var/video/id/video.mp4
             File convertFile = new File("Storage/var/video/"+ id +"/video.mp4");
             convertFile.createNewFile();

             try (FileOutputStream fout = new FileOutputStream(convertFile)){
                 fout.write(file.getBytes());
             }
             catch (Exception exe)
             {
                 exe.printStackTrace();
             }

            /*//Sends an HTTP REST POST to the vps
             String command = "curl -X POST \\\n" +
                     "  http://localhost:8082/videos/process \\\n" +
                     "  -H 'Content-Type: application/json' \\\n" +
                     "  -H 'Postman-Token: 4e8f78b6-c87b-44a9-a05b-412034121d74' \\\n" +
                     "  -H 'cache-control: no-cache' \\\n" +
                     "  -d '{\"videoId\":"+id+"}'";

             Process process = Runtime.getRuntime().exec(command);*/

            return new ResponseEntity<>("Video Uploaded", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
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