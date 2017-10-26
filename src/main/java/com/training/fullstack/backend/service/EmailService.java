package com.training.fullstack.backend.service;

import com.training.fullstack.web.domain.Feedback;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendFeedbackEmail(Feedback feedback);
    void sendGenericEmailMessage(SimpleMailMessage mailMessage);
}
