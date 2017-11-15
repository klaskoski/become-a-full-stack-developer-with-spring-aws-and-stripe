package com.training.fullstack.common.util;


import com.training.fullstack.backend.domain.backend.User;
import com.training.fullstack.web.controllers.ForgotMyPasswordController;
import com.training.fullstack.web.domain.frontend.BasicAccountPayload;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mock.web.MockHttpServletRequest;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.UUID;

public class UserUtilsTest {

    private MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
    private PodamFactory podamFactory = new PodamFactoryImpl();

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

    @Test
    public void shouldFillPersistenceObjectCorrectly() throws Exception {
        BasicAccountPayload webUser = podamFactory.manufacturePojoWithFullData(BasicAccountPayload.class);
        webUser.setEmail("test@example.com");

        User user = UserUtils.fromWebUserToDomainUser(webUser);

        Assert.assertEquals(webUser.getUsername(), user.getUsername());
        Assert.assertEquals(webUser.getEmail(), user.getEmail());
        Assert.assertEquals(webUser.getPassword(), user.getPassword());
        Assert.assertEquals(webUser.getPhoneNumber(), user.getPhoneNumber());
    }
}