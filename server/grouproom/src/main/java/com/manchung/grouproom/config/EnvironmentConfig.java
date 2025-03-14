package com.manchung.grouproom.config;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class EnvironmentConfig {
    private final Environment environment;

    @PostConstruct
    public void setActiveProfiles() {
        String devProfile = environment.getProperty("SPRING_PROFILES_ACTIVE_DEV");
        String prodProfile = environment.getProperty("SPRING_PROFILES_ACTIVE_PROD");

        if (devProfile != null) {
            System.setProperty("spring.profiles.active", "dev");
        }

        if (prodProfile != null) {
            System.setProperty("spring.profiles.active", "prod");
        }
    }
}
