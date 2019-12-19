package com.dslab.homework1.videomanagementservice.service;

import com.dslab.homework1.videomanagementservice.entity.UserRepository;
import com.dslab.homework1.videomanagementservice.entity.Video;
import com.dslab.homework1.videomanagementservice.entity.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class VmsVideoService {

    @Autowired
    VideoRepository Vrepository;

    @Autowired
    UserRepository Urepository;

    public Video addVideo (String email,Video video){
        video.setUser(Urepository.findByEmail(email));
        video.setState("Not Uploaded");
        return Vrepository.save(video);
    }

    /*public Optional<Video> getById (Integer id){
        return Vrepository.findById(id);
    }*/

    public Iterable<Video> getAllVideos(){
        return Vrepository.findAll();
    }

    public boolean VideoCheck (String email,Integer id){
        if (Vrepository.existsById(id)){
            if(Vrepository.findById(id).get().getUser().getEmail().equals(email)){
                Vrepository.findById(id).get().setState("Uploaded");
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }
}
