package com.training.fullstack.common.config;

import com.training.fullstack.backend.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import static com.training.fullstack.web.controllers.ForgotMyPasswordController.FORGOT_MY_PASSWORD_URL;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserSecurityService userSecurityService;

    @Autowired
    private Environment env;

    private static final String SALT = "iadijn[ps908378hkamsdk...ij0-";

    @Bean
    public BCryptPasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder(6, new SecureRandom(SALT.getBytes()));
    }

    private static final String[] PUBLIC_METHODS = {
            "/webjars/**",
            "/css/**",
            "/js/**",
            "/",
            "/about/**",
            "/contact/**",
            "/error/**/*",
            "/h2-console/**",
            FORGOT_MY_PASSWORD_URL
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        List<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains("dev")){
            http.csrf().disable();
            http.headers().frameOptions().disable();
        }

        http.authorizeRequests()
                .antMatchers(PUBLIC_METHODS).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/payload")
                .failureUrl("/login?error").permitAll()
                .and()
                .logout().permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userSecurityService)
                .passwordEncoder(passwordEncoder());
    }
}
