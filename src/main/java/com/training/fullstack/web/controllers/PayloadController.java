package com.training.fullstack.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PayloadController {

    static final String PAYLOAD_VIEW_NAME = "payload/payload";

    @GetMapping("/payload")
    public String payload(){
        return PAYLOAD_VIEW_NAME;
    }
}
