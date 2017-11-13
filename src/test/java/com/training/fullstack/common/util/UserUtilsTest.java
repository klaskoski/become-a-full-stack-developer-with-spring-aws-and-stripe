package com.training.fullstack.common.util;


import com.training.fullstack.web.controllers.ForgotMyPasswordController;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.UUID;

public class UserUtilsTest {

    private MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

    @Test
    public void passwordResetEmailUrlShouldBeCreatedCorrectly() throws Exception {

        mockHttpServletRequest.setServerPort(8080);
        String token = UUID.randomUUID().toString();
        long userId = 123456;

        String expectedUrl = "http://localhost:8080" +
                ForgotMyPasswordController.CHANGE_PASSWORD_PATH + "?id=" + userId + "&token=" + token;
        String actualUrl = UserUtils.createPasswordResetUrl(mockHttpServletRequest, userId, token);

        Assert.assertEquals(expectedUrl, actualUrl);
    }
}