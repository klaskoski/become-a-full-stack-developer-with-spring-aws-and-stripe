package com.training.fullstack.test.integration;

import com.training.fullstack.backend.domain.backend.PasswordResetToken;
import com.training.fullstack.backend.domain.backend.User;
import com.training.fullstack.backend.repositories.PasswordResetTokenRepository;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordResetTokenIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Rule
    public TestName testName = new TestName();

    @Test
    public void tokenShouldExpire() throws Exception {
        User user = createUser(testName.getMethodName(), testName.getMethodName() + "@testname.com");
        Assert.assertNotNull(user);

        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        String token = UUID.randomUUID().toString();

        LocalDateTime expectedTime = now.plusMinutes(120);

        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, now, 120);

        LocalDateTime actualTime = passwordResetToken.getExpiryDate();
        Assert.assertNotNull(actualTime);
        Assert.assertEquals(expectedTime, actualTime);
    }

    @Test
    public void findTokenByValue() throws Exception {
        User user = createUser(testName.getMethodName(), testName.getMethodName() + "@testname.com");
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, now, 120);
        passwordResetTokenRepository.save(passwordResetToken);
        Assert.assertNotNull(passwordResetTokenRepository.findByToken(token));
    }
}
