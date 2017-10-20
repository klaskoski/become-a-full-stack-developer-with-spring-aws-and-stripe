package com.training.fullstack.web.controllers;

import com.training.fullstack.web.domain.Feedback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ContactController {

    private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);
    private static final String CONTACT_PAGE = "contact/contact";
    private static final String FEEDBACK = "feedback";

    @GetMapping("/contact")
    public String contactGet(ModelMap model){
        Feedback feedback = new Feedback();
        model.addAttribute(FEEDBACK, feedback);
        return CONTACT_PAGE;
    }

    @PostMapping("/contact")
    public String contactPut(@ModelAttribute Feedback feedback){
        LOG.debug("Feedback content {}",feedback);
        return CONTACT_PAGE;
    }
}
