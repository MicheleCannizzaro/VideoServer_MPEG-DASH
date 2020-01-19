package com.dslab.homework1.videomanagementservice.service;

import com.dslab.homework1.videomanagementservice.entity.User;
import com.dslab.homework1.videomanagementservice.entity.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class VmsUserService {

    @Autowired
    UserRepository repository;

    public User addUser(User user){
        user.setRoles(Collections.singletonList("USER"));
        return repository.save(user);
    }

/*    public Iterable<User> getAllUSers(){
        return repository.findAll();
    }

    public User getByEmail(String email){
        return repository.findByEmail(email);
    }*/

   /* public String deleteUser(String email){
        repository.deleteByEmail(email);
        return String.format("The user with email %s has been deleted!", email);
    }*/

    /*public Optional<User> getById(Integer id){
        return repository.findById(id);
    }*/
}