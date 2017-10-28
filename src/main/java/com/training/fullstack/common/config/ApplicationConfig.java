package com.training.fullstack.common.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.training.fullstack.backend.repositories")
@EntityScan(basePackages = "com.training.fullstack.backend.domain.backend")
@EnableTransactionManagement
public class ApplicationConfig {
}
