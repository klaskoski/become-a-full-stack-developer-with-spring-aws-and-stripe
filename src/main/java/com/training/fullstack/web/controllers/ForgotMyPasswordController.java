package com.training.fullstack.web.controllers;

import com.training.fullstack.backend.domain.backend.PasswordResetToken;
import com.training.fullstack.backend.domain.backend.User;
import com.training.fullstack.backend.service.EmailService;
import com.training.fullstack.backend.service.PasswordResetTokenService;
import com.training.fullstack.backend.service.UserService;
import com.training.fullstack.common.util.UserUtils;
import com.training.fullstack.backend.service.I18NService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ForgotMyPasswordController {

    public static final String FORGOT_MY_PASSWORD_URL = "/forgotmypassword";
    public static final String CHANGE_PASSWORD_PATH = "/changeuserpassword";
    private static final String EMAIL_ADDRESS_VIEW_NAME = "forgotmypassword/emailForm";
    private static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordController.class);
    private static final String EMAIL_MESSAGE_TEXT_PROPERTY_NAME = "forgotmypassword.email.text";

    @Autowired
    private I18NService i18NService;

    @Autowired
    private EmailService emailService;

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
            User user = passwordResetToken.getUser();
            String token = passwordResetToken.getToken();
            String resetPasswordUrl = UserUtils.createPasswordResetUrl(request, user.getId(), token);
            LOG.info("Mail has been sent to user {}", passwordResetToken.getUser().getUsername());

            String emailText = i18NService.getMessage(EMAIL_MESSAGE_TEXT_PROPERTY_NAME, request.getLocale());
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("[Lalala]: How to reset Your Password");
            mailMessage.setText(emailText + "\r\n" + resetPasswordUrl);
            mailMessage.setFrom("codeyourbest@wp.pl");

            emailService.sendGenericEmailMessage(mailMessage);
        }

        model.addAttribute("mailSent", true);
        return EMAIL_ADDRESS_VIEW_NAME;
    }
}
