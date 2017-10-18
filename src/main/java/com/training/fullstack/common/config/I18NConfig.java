package com.training.fullstack.common.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class I18NConfig {

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource resourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("classpath:i18n/messages");
        resourceBundleMessageSource.setCacheSeconds(10);
        return resourceBundleMessageSource;
    }

}
