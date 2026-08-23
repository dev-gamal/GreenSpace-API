package com.greenspace.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtSecret", "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtTokenProvider, "jwtExpirationMs", 3600000L); // 1 heure
    }

    @Test
    void generateToken_ShouldReturnValidJwt() {
        // Arrange
        Authentication authentication = new UsernamePasswordAuthenticationToken("gamal@test.com", "password", Collections.emptyList());

        // Act
        String token = jwtTokenProvider.generateToken(authentication);

        // Assert
        assertNotNull(token);
        assertTrue(token.startsWith("eyJ"));
    }

    @Test
    void getEmailFromToken_ShouldReturnCorrectEmail() {
        // Arrange
        Authentication authentication = new UsernamePasswordAuthenticationToken("gamal@test.com", null, Collections.emptyList());
        String token = jwtTokenProvider.generateToken(authentication);

        // Act
        String email = jwtTokenProvider.getEmailFromToken(token);

        // Assert
        assertEquals("gamal@test.com", email);
    }

    @Test
    void validateToken_ShouldReturnTrue_WhenTokenIsValid() {
        // Arrange
        Authentication authentication = new UsernamePasswordAuthenticationToken("gamal@test.com", null, Collections.emptyList());
        String token = jwtTokenProvider.generateToken(authentication);

        // Act
        boolean isValid = jwtTokenProvider.validateToken(token);

        // Assert
        assertTrue(isValid);
    }
}