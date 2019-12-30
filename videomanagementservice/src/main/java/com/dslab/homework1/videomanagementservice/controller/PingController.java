package com.dslab.homework1.videomanagementservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/ping")
public class PingController {

    @GetMapping(path = "")
    public String ping() {
        return "pong";
    }
}
