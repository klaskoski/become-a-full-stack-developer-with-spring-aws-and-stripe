package com.training.fullstack.test.integration;

import com.training.fullstack.backend.domain.backend.*;
import com.training.fullstack.backend.repositories.PlanRepository;
import com.training.fullstack.backend.repositories.RoleRepository;
import com.training.fullstack.backend.repositories.UserRepository;
import com.training.fullstack.common.util.UserUtils;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class AbstractIntegrationTest {

    @Autowired
    protected PlanRepository planRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected UserRepository userRepository;

    @Rule
    public TestName testName = new TestName();

    protected User createUser(String username, String email) {
        Plan plan =  new Plan(PlanEnum.BASIC);
        planRepository.save(plan);

        User user = UserUtils.createBasicUser(username, email);
        user.setPlan(plan);

        Role adminRole = new Role(RoleEnum.BASIC);
        roleRepository.save(adminRole);

        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole(user, adminRole);
        userRoles.add(userRole);

        user.getUserRoles().addAll(userRoles);
        user = userRepository.save(user);
        return user;
    }
}
