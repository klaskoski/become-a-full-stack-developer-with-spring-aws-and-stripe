package com.training.fullstack.common.util;

import com.training.fullstack.backend.domain.backend.User;
import com.training.fullstack.web.controllers.ForgotMyPasswordController;
import com.training.fullstack.web.domain.frontend.BasicAccountPayload;

import javax.servlet.http.HttpServletRequest;

public class UserUtils {

    private UserUtils(){
        throw new AssertionError("Not istantiable");
    }

    public static User createBasicUser(String username, String email) {

        User user = new User();
        user.setUsername(username);
        user.setPassword("secret");
        user.setEmail(email);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhoneNumber("123123123");
        user.setCountry("PL");
        user.setEnabled(true);
        user.setDescription("A basic user");
        user.setProfileImageUrl("url");
        return user;
    }

    public static String createPasswordResetUrl(HttpServletRequest request, long userId, String token) {
        return request.getScheme() +
                "://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                ForgotMyPasswordController.CHANGE_PASSWORD_PATH +
                "?id=" +
                userId +
                "&token=" +
                token;
    }

    public static <T extends BasicAccountPayload> User fromWebUserToDomainUser(T webUser) {
        User user = new User();
        user.setUsername(webUser.getUsername());
        user.setPassword(webUser.getPassword());
        user.setFirstName(webUser.getFirstName());
        user.setLastName(webUser.getLastName());
        user.setEmail(webUser.getEmail());
        user.setPhoneNumber(webUser.getPhoneNumber());
        user.setCountry(webUser.getCountry());
        user.setDescription(webUser.getDescription());
        user.setEnabled(true);

        return user;
    }
}
