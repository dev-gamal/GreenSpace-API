package com.greenspace.service;

import com.greenspace.dto.request.UserRegistrationRequest;
import com.greenspace.dto.response.UserResponse;
import com.greenspace.entity.User;
import com.greenspace.enums.Role;
import com.greenspace.mapper.UserMapper;
import com.greenspace.repository.UserRepository;
import com.greenspace.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRegistrationRequest request;
    private User user;
    private UserResponse response;

    @BeforeEach
    void setUp() {
        request = UserRegistrationRequest.builder()
                .firstName("Gamal")
                .lastName("Badie")
                .email("gamal@test.com")
                .password("password123")
                .role(Role.GARDENER)
                .build();

        user = User.builder()
                .id(1L)
                .email("gamal@test.com")
                .password("encodedPassword")
                .role(Role.GARDENER)
                .isBlocked(false)
                .build();

        response = UserResponse.builder()
                .id(1L)
                .email("gamal@test.com")
                .build();
    }

    @Test
    void registerUser_ShouldReturnUserResponse_WhenEmailIsUnique() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userMapper.toEntity(any(UserRegistrationRequest.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponse(any(User.class))).thenReturn(response);

        // Act
        UserResponse result = userService.registerUser(request);

        // Assert
        assertNotNull(result);
        assertEquals("gamal@test.com", result.getEmail());
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("password123");
    }

    @Test
    void registerUser_ShouldThrowException_WhenEmailAlreadyExists() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(request);
        });

        assertEquals("This email exists.", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }
}