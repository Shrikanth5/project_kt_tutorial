package com.kt.tutorial.service;

import com.kt.tutorial.dto.auth.LoginRequest;
import com.kt.tutorial.dto.auth.LoginResponse;
import com.kt.tutorial.model.User;
import com.kt.tutorial.repository.UserRepository;
import com.kt.tutorial.security.JwtTokenProvider;
import com.kt.tutorial.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    private User user;
    private LoginRequest loginRequest;
    private String jwtToken;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setName("Test User");
        user.setRoles(new HashSet<>());

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        jwtToken = "jwt.token.here";

        lenient().when(authentication.getPrincipal()).thenReturn(UserPrincipal.create(user));
        lenient().when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        lenient().when(tokenProvider.generateToken(authentication)).thenReturn(jwtToken);
        lenient().when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
    }

    @Test
    void login_ShouldReturnLoginResponse() {
        LoginResponse response = userService.login(loginRequest);

        assertNotNull(response);
        assertEquals(jwtToken, response.getToken());
        assertEquals(user.getEmail(), response.getEmail());
        assertEquals(user.getName(), response.getName());
        assertEquals(user.getRoles(), response.getRoles());
    }

    @Test
    void createUser_Success() {
        // Arrange
        User newUser = new User();
        newUser.setEmail("new@example.com");
        newUser.setPassword("password");
        newUser.setName("New User");

        when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(newUser.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Act
        User createdUser = userService.createUser(newUser);

        // Assert
        assertNotNull(createdUser);
        assertEquals(newUser.getEmail(), createdUser.getEmail());
        assertEquals(newUser.getName(), createdUser.getName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_EmailAlreadyExists() {
        // Arrange
        User newUser = new User();
        newUser.setEmail("existing@example.com");
        when(userRepository.existsByEmail(newUser.getEmail())).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.createUser(newUser));
        verify(userRepository, never()).save(any(User.class));
    }
} 