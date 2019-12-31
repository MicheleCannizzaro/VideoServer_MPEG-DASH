package com.dslab.homework1.videomanagementservice.service;

import com.dslab.homework1.videomanagementservice.entity.UserRepository;
import com.dslab.homework1.videomanagementservice.entity.Video;
import com.dslab.homework1.videomanagementservice.entity.VideoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@Service
@Transactional
public class VmsVideoService {

    @Autowired
    VideoRepository Vrepository;

    @Autowired
    UserRepository Urepository;

    public Video addVideo(String email, Video video) {
        video.setUser(Urepository.findByEmail(email));
        video.setState("Not Uploaded");
        return Vrepository.save(video);
    }

    public boolean encodeVideo(String user_name, Integer id, MultipartFile file) throws IOException {

        //Take a file "video.mp4" as input and save it into the storage on path "/var/video/id/video.mp4"

        if(VideoCheckPOST(user_name,id)){

            //Create path missing directories
            new File("/Storage/var/video/" + id).mkdirs();

            File convertFile = new File("/Storage/var/video/" + id + "/video.mp4");

            if(!convertFile.createNewFile()) {
                return false;
            }

            try (FileOutputStream fout = new FileOutputStream(convertFile)) {
                fout.write(file.getBytes());
            } catch (Exception exe) {
                exe.printStackTrace();
            }

            //Sends an HTTP REST POST to the vps

            final String vps_uri = "http://vps:8080/videos/process";

            RestTemplate restTemplate = new RestTemplate();
            org.springframework.http.HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject videoRequest = new JSONObject();
            videoRequest.put("videoId", id);

            HttpEntity<String> request = new HttpEntity<String>(videoRequest.toString(), headers);

            restTemplate.postForObject(vps_uri, request, String.class);

            Vrepository.findById(id).get().setState("Uploaded");

            return true;
        }
        return false;
    }


    /*public Optional<Video> getById (Integer id){
        return Vrepository.findById(id);
    }*/

    public Iterable<Video> getAllVideos(){
        return Vrepository.findAll();
    }

    public boolean VideoCheckPOST (String email,Integer id){
        if (Vrepository.existsById(id)){
            if(Vrepository.findById(id).get().getUser().getEmail().equals(email)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    public boolean VideoCheckGET (Integer id){
        File video_f = new File("/Storage/var/videofiles/"+id+"/video.mpd");
        if (Vrepository.existsById(id) && video_f.exists()){
            return true;
        }else{
            return false;
        }
    }

}
