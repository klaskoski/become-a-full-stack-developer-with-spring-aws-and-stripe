package com.training.fullstack.common.util;

import com.training.fullstack.backend.domain.backend.User;

public class UserUtils {

    private UserUtils(){
        throw new AssertionError("Not istantiable");
    }

    public static User createBasicUser(){

        User user = new User();
        user.setUsername("basicUser");
        user.setPassword("secret");
        user.setEmail("lala@gmail.com");
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setPhoneNumber("123123123");
        user.setCountry("PL");
        user.setEnabled(true);
        user.setDescription("A basic user");
        user.setProfileImageUrl("url");
        return user;
    }
}
