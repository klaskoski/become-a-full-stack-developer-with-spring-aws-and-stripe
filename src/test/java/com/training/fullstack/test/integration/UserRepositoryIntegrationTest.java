package com.training.fullstack.test.integration;

import com.training.fullstack.backend.domain.backend.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Rule
    public TestName testName = new TestName();

    @Before
    public void init() {
        Assert.assertNotNull(planRepository);
        Assert.assertNotNull(roleRepository);
        Assert.assertNotNull(userRepository);
    }

    @Test
    public void testCreateNewPlan() throws Exception {
        Plan basicPlan = new Plan(PlanEnum.BASIC);
        planRepository.save(basicPlan);
        Plan retrievedPlan = planRepository.findOne(PlanEnum.BASIC.getId());
        Assert.assertNotNull(retrievedPlan);
    }

    @Test
    public void testCreateNewRole() throws Exception {
        Role userRole  = new Role(RoleEnum.BASIC);
        roleRepository.save(userRole);

        Role retrievedRole = roleRepository.findOne(RoleEnum.BASIC.getId());
        Assert.assertNotNull(retrievedRole);
    }

    @Test
    public void createNewUser() throws Exception {

        User user = createUser(testName.getMethodName());
        User newlyCreatedUser = userRepository.findOne(user.getId());
        Assert.assertNotNull(newlyCreatedUser);
        Assert.assertTrue(newlyCreatedUser.getId() != 0);
        Assert.assertNotNull(newlyCreatedUser.getPlan());
        Set<UserRole> newlyCreatedUserUserRoles = newlyCreatedUser.getUserRoles();
        for (UserRole ur : newlyCreatedUserUserRoles) {
            Assert.assertNotNull(ur.getRole());
        }

    }

    @Test
    public void deleteUser() throws Exception {
        User basicUser = createUser(testName.getMethodName());
        userRepository.delete(basicUser.getId());
    }

    @Test
    public void getUserByEmail() throws Exception {
        User user = createUser(testName.getMethodName());

        User createdUser = userRepository.findByEmail(user.getEmail());
        Assert.assertNotNull(createdUser);
        Assert.assertEquals(user, createdUser);
    }

    @Test
    public void updateUserPassword() throws Exception {
        User user = createUser(testName.getMethodName());

        String newPassword = UUID.randomUUID().toString();

        userRepository.updateUserPassword(user.getId(), newPassword);
        user = userRepository.findOne(user.getId());
        Assert.assertEquals(newPassword, user.getPassword());
    }
}