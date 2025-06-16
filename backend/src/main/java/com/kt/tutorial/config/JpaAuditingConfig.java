package com.kt.tutorial.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@Profile("!test")
@EnableJpaAuditing
public class JpaAuditingConfig {
    // Enables JPA Auditing only when not in 'test' profile
} 