package com.training.fullstack.web.controllers;

import com.training.fullstack.web.domain.frontend.BasicAccountPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {
    private static final Logger LOG = LoggerFactory.getLogger(SignupController.class);

    public static final String SIGNUP_URL_MAPPING = "/signup";
    public static final String PAYLOAD_MODEL_KEY_NAME = "payload";
    public static final String SUBSCRIPTION_VIEW_NAME = "registration/signup";

    @GetMapping(SIGNUP_URL_MAPPING)
    public String signupGet(@RequestParam("planId") int planId, ModelMap model){
        model.addAttribute(PAYLOAD_MODEL_KEY_NAME, new BasicAccountPayload());
        return SUBSCRIPTION_VIEW_NAME;
    }
}
