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

    //5. POST http://localhost:8080/vms/register
    @PostMapping(path = "register")
    public @ResponseBody User register(@RequestBody User user) {
        return userService.addUser(user);
    }

}