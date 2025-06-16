package com.kt.tutorial.dto.auth;

import lombok.Data;
import java.util.Set;

@Data
public class LoginResponse {
    private String token;
    private String email;
    private String name;
    private Set<String> roles;
} 