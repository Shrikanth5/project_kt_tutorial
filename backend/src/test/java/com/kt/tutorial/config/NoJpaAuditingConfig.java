package com.kt.tutorial.config;

import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class NoJpaAuditingConfig {
    // Prevents scanning of @EnableJpaAuditing in main context for tests
} 