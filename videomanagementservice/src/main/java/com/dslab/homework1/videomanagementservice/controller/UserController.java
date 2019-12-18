package com.dslab.homework1.videomanagementservice.controller;

import com.dslab.homework1.videomanagementservice.entity.User;
import com.dslab.homework1.videomanagementservice.service.VmsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping(path = "/vms")
public class UserController {

    @Autowired
    VmsUserService userService;

    //POST http://localhost:8080/vms/register
    @PostMapping(path = "register")
    public @ResponseBody User register(@RequestBody User user) {
        return userService.addUser(user);
    }

    /*//GET http://localhost:8080/user/all
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAll() {
        return userService.getAllUSers();
    }*/

    /*//GET http://localhost:8080/user/1
    @GetMapping(path = "/{email}")
    public @ResponseBody User getUser(Authentication auth, @PathVariable String email) {
        if(email.equalsIgnoreCase(auth.getName()) || isAdmin(auth))
            return userService.getByEmail(email);
        else return null;
    }*/

    //in +
    /*@GetMapping(path = "/{id}")
    public @ResponseBody Optional<User> getUser (@PathVariable Integer id) {
        return userService.getById(id);
    }*/

    /*//DELETE http://localhost:8080/user/1
    @DeleteMapping(path = "/{email}")
    public @ResponseBody String deleteUser (Authentication auth, @PathVariable String email) {
        if(email.equalsIgnoreCase(auth.getName()) || isAdmin(auth))
            return userService.deleteUser(email);
        else return null;
    }*/


    /*private boolean isAdmin(Authentication auth){
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ADMIN")) {
                return true;
            }
        }
        return false;
    }*/
}