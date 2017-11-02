package com.training.fullstack.test.integration;

import com.training.fullstack.backend.domain.backend.*;
import com.training.fullstack.backend.service.UserService;
import com.training.fullstack.common.util.UserUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void createNewUser() throws Exception {

        User user = UserUtils.createBasicUser("TestUSer2", "testUser2@gmail.com");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, new Role(RoleEnum.ADMIN)));

        user = userService.createUser(user, PlanEnum.BASIC, userRoles);

        Assert.assertNotNull(user);
        Assert.assertNotEquals(0, user.getId());
    }
}
