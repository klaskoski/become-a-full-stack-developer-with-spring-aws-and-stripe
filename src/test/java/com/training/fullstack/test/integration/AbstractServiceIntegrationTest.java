package com.training.fullstack.test.integration;

import com.training.fullstack.backend.domain.backend.*;
import com.training.fullstack.backend.service.UserService;
import com.training.fullstack.common.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class AbstractServiceIntegrationTest {
    @Autowired
    protected UserService userService;

    protected User createUser(String username) {
        User user = UserUtils.createBasicUser(username, username + "@gmail.com");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, new Role(RoleEnum.ADMIN)));

        user = userService.createUser(user, PlanEnum.BASIC, userRoles);
        return user;
    }
}
