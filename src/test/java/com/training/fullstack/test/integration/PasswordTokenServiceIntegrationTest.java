package com.training.fullstack.test.integration;

import com.training.fullstack.backend.domain.backend.PasswordResetToken;
import com.training.fullstack.backend.domain.backend.User;
import com.training.fullstack.backend.service.PasswordResetTokenService;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordTokenServiceIntegrationTest extends AbstractServiceIntegrationTest {

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Rule
    public TestName testName = new TestName();

    @Test
    public void createNewTokenForUserEmail() throws Exception {
        User user = createUser(testName.getMethodName());

        PasswordResetToken passwordResetToken =
                passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());

        Assert.assertNotNull(passwordResetToken);
    }

    @Test
    public void findByToken() throws Exception {
        User user = createUser(testName.getMethodName());

        PasswordResetToken passwordResetToken =
                passwordResetTokenService.createPasswordResetTokenForEmail(user.getEmail());

        Assert.assertNotNull(passwordResetTokenService.findByToken(passwordResetToken.getToken()));
    }
}
