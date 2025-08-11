package com.share.trip.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.trip.model.User;
import com.share.trip.repository.UserRepository;
import com.share.trip.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testRegister_Success() throws Exception {
        User newUser = new User();
        newUser.setEmail("john@example.com");
        newUser.setPassword("password");

        Mockito.when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(any(User.class))).thenReturn(newUser);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testRegister_UserAlreadyExists() throws Exception {
        User existingUser = new User();
        existingUser.setEmail("john@example.com");

        Mockito.when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(existingUser));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingUser)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User already exists"));
    }

    @Test
    void testLogin_Success() throws Exception {
        User loginRequest = new User();
        loginRequest.setEmail("john@example.com");
        loginRequest.setPassword("password");

        Authentication auth = new UsernamePasswordAuthenticationToken("john@example.com", null);

        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);
        Mockito.when(jwtUtil.generateToken(eq("john@example.com"),eq("test"))).thenReturn("jwt-token");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("jwt-token"));
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        User loginRequest = new User();
        loginRequest.setEmail("john@example.com");
        loginRequest.setPassword("wrong");

        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Bad credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

}
