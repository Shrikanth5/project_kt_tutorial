package com.kt.tutorial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kt.tutorial.dto.auth.LoginRequest;
import com.kt.tutorial.dto.auth.LoginResponse;
import com.kt.tutorial.model.User;
import com.kt.tutorial.service.UserService;
import com.kt.tutorial.security.JwtTokenProvider;
import com.kt.tutorial.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    public void testLogin() throws Exception {
        // Create test data
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("jwt.token.here");
        loginResponse.setEmail("test@example.com");
        loginResponse.setName("Test User");
        loginResponse.setRoles(new HashSet<>());

        // Mock service behavior
        when(userService.login(any(LoginRequest.class))).thenReturn(loginResponse);

        // Perform test
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(loginResponse.getToken()))
                .andExpect(jsonPath("$.email").value(loginResponse.getEmail()))
                .andExpect(jsonPath("$.name").value(loginResponse.getName()))
                .andExpect(jsonPath("$.roles").isArray());
    }

    @Test
    public void testRegister() throws Exception {
        // Create test data
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setPassword("password123");
        user.setRoles(new HashSet<>());

        // Mock service behavior
        when(userService.createUser(any(User.class))).thenReturn(user);

        // Perform test
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));
    }
} 