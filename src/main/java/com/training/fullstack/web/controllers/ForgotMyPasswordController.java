package com.training.fullstack.web.controllers;

import com.training.fullstack.backend.domain.backend.PasswordResetToken;
import com.training.fullstack.backend.service.PasswordResetTokenService;
import com.training.fullstack.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ForgotMyPasswordController {

    public static final String FORGOT_MY_PASSWORD_URL = "/forgotmypassword";
    private static final String EMAIL_ADDRESS_VIEW_NAME = "forgotmypassword/emailForm";
    public static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @GetMapping(FORGOT_MY_PASSWORD_URL)
    public String forgotPasswordGet(){
        return EMAIL_ADDRESS_VIEW_NAME;
    }

    @PostMapping(FORGOT_MY_PASSWORD_URL)
    public String forgotPasswordPost(HttpServletRequest request,
                                     @RequestParam("email") String email,
                                     ModelMap model) {

        PasswordResetToken passwordResetToken =
                passwordResetTokenService.createPasswordResetTokenForEmail(email);
        if (passwordResetToken != null) {
            LOG.info("Mail has been sent to user {}", passwordResetToken.getUser().getUsername());
        }

        model.addAttribute("mailSent", true);
        return EMAIL_ADDRESS_VIEW_NAME;
    }
}
