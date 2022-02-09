package com.koxx4.simpleworkout.simpleworkoutserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Profile("dev")
@EnableJpaRepositories(basePackages = {"com.koxx4.simpleworkout.simpleworkoutserver.repositories"})
public class DevDatabaseConfig {
}
