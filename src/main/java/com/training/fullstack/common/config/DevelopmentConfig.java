package com.training.fullstack.common.config;

import com.training.fullstack.backend.service.EmailService;
import com.training.fullstack.backend.service.MockEmailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("dev")
@PropertySource("classpath:/application-dev.properties")
public class DevelopmentConfig {

    @Bean
    EmailService emailService(){
        return new MockEmailService();
    }
}
