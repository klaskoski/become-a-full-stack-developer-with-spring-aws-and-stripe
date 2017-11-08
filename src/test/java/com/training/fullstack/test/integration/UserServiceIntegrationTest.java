package com.training.fullstack.test.integration;

import com.training.fullstack.backend.domain.backend.User;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Rule
    public TestName testName = new TestName();

    @Test
    public void createNewUser() throws Exception {
        User user = createUser(testName.toString());

        Assert.assertNotNull(user);
        Assert.assertNotEquals(0, user.getId());
    }
}
