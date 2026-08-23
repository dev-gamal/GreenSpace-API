package com.greenspace.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenspace.dto.request.UserLoginRequest;
import com.greenspace.dto.request.UserRegistrationRequest;
import com.greenspace.dto.response.UserResponse;
import com.greenspace.enums.Role;
import com.greenspace.security.JwtTokenProvider;
import com.greenspace.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider tokenProvider;

    private UserRegistrationRequest registrationRequest;
    private UserLoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        registrationRequest = UserRegistrationRequest.builder()
                .firstName("Gamal")
                .lastName("Badie")
                .email("gamal@test.com")
                .password("password123")
                .role(Role.GARDENER)
                .build();

        loginRequest = new UserLoginRequest("gamal@test.com", "password123");
    }

    @Test
    void register_ShouldReturn201_WhenRequestIsValid() throws Exception {
        UserResponse response = UserResponse.builder().email("gamal@test.com").build();
        when(userService.registerUser(any(UserRegistrationRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("gamal@test.com"));
    }

    @Test
    void register_ShouldReturn400_WhenEmailIsInvalid() throws Exception {
        registrationRequest.setEmail("invalid-email");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_ShouldReturn200AndToken_WhenCredentialsAreValid() throws Exception {
        Authentication auth = new UsernamePasswordAuthenticationToken("gamal@test.com", "password123");
        UserResponse response = UserResponse.builder().email("gamal@test.com").build();

        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);
        when(tokenProvider.generateToken(any(Authentication.class))).thenReturn("mocked-jwt-token");
        when(userService.getUserByEmail(anyString())).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("mocked-jwt-token"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.user.email").value("gamal@test.com"));
    }
}