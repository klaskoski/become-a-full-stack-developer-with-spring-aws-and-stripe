package com.training.fullstack.backend.service;

import com.training.fullstack.backend.domain.backend.PasswordResetToken;
import com.training.fullstack.backend.domain.backend.User;
import com.training.fullstack.backend.repositories.PasswordResetTokenRepository;
import com.training.fullstack.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Transactional
    public PasswordResetToken createPasswordResetTokenForEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        String token = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());

        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, now, 120);
        passwordResetTokenRepository.save(passwordResetToken);
        return passwordResetToken;
    }
}
