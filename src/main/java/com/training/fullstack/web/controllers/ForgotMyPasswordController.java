package com.training.fullstack.web.controllers;

import com.training.fullstack.backend.domain.backend.PasswordResetToken;
import com.training.fullstack.backend.domain.backend.User;
import com.training.fullstack.backend.service.EmailService;
import com.training.fullstack.backend.service.PasswordResetTokenService;
import com.training.fullstack.backend.service.UserService;
import com.training.fullstack.common.util.UserUtils;
import com.training.fullstack.backend.service.I18NService;
import org.codehaus.groovy.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Locale;

@Controller
public class ForgotMyPasswordController {

    public static final String FORGOT_MY_PASSWORD_URL = "/forgotmypassword";
    public static final String CHANGE_PASSWORD_PATH = "/changeuserpassword";
    private static final String CHANGE_PASSWORD_VIEW_NAME = "forgotmypassword/changePassword";
    private static final String EMAIL_ADDRESS_VIEW_NAME = "forgotmypassword/emailForm";
    private static final Logger LOG = LoggerFactory.getLogger(ForgotMyPasswordController.class);
    private static final String EMAIL_MESSAGE_TEXT_PROPERTY_NAME = "forgotmypassword.email.text";
    private static final String PASSWORD_RESET_ATTRIBUTE_NAME = "passwordReset";
    private static final String MESSAGE_ATTRIBUTE_NAME = "message";


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


    @GetMapping(CHANGE_PASSWORD_PATH)
    public String changePasswordGet(@RequestParam("id") long id,
                                    @RequestParam("token") String token,
                                    Locale locale,
                                    ModelMap model) {

        if (StringUtils.isEmpty(token) || id == 0){
            LOG.error("invalid user id {} or token value {}", id, token);
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "Invalid user id or token value");
            return CHANGE_PASSWORD_VIEW_NAME;
        }

        PasswordResetToken passwordResetToken = passwordResetTokenService.findByToken(token);

        if (passwordResetToken == null) {
            LOG.error("token could not be found with value {}", token);
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "Token not found");
            return CHANGE_PASSWORD_VIEW_NAME;
        }

        User user = passwordResetToken.getUser();

        if (user.getId() != id) {
            LOG.error("User id {} and id are different", user.getId(), id);
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "Invalid Token");
            return CHANGE_PASSWORD_VIEW_NAME;
        }

        if (LocalDateTime.now(Clock.systemUTC()).isAfter(passwordResetToken.getExpiryDate())) {
            LOG.error("The token {} has expired", token);
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "Token expired");
            return CHANGE_PASSWORD_VIEW_NAME;
        }

        model.addAttribute("principalId", user.getId());

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, user.getAuthorities(
        ));
        SecurityContextHolder.getContext().setAuthentication(auth);

        return CHANGE_PASSWORD_VIEW_NAME;
    }

    @PostMapping(CHANGE_PASSWORD_PATH)
    public String changePasswordPost(@RequestParam("principal_id") long userId,
                                     @RequestParam("password") String password,
                                     ModelMap model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            LOG.error("Unauthenticated user tried to invoke the reset password POST method");
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "You are not authorized to perform this request");
            return CHANGE_PASSWORD_VIEW_NAME;
        }

        User user = (User) authentication.getPrincipal();
        if (user.getId() != userId) {
            LOG.error("User {} is trying to change another users password {}", user.getId(), userId);
            model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "false");
            model.addAttribute(MESSAGE_ATTRIBUTE_NAME, "You are not authorized to perform this request");
            return CHANGE_PASSWORD_VIEW_NAME;
        }
        userService.updateUserPassword(userId, password);
        LOG.info("Password succesfully updated for user {}", user.getUsername());

        model.addAttribute(PASSWORD_RESET_ATTRIBUTE_NAME, "true");
        return CHANGE_PASSWORD_VIEW_NAME;
    }
}
