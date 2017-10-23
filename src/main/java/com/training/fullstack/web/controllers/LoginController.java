package com.training.fullstack.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    static final String LOGIN_VIEW_NAME = "user/login";

    @GetMapping
    public String login (){
        return LOGIN_VIEW_NAME;
    }
}
