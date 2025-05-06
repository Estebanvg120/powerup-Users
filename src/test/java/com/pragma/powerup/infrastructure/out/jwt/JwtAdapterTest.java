package com.pragma.powerup.infrastructure.out.jwt;


import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.infrastructure.config.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtAdapterTest {

    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;
    private JwtAdapter jwtAdapter;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        passwordEncoder = mock(PasswordEncoder.class);
        jwtProvider = mock(JwtProvider.class);
        jwtAdapter = new JwtAdapter(passwordEncoder, jwtProvider);

        userModel = new UserModel();
        userModel.setEmail("test@example.com");
        userModel.setPassword("encryptedPassword");
    }

    @Test
    void testGenerateToken_ReturnsToken() {
        // Arrange
        String expectedToken = "mocked.jwt.token";
        when(jwtProvider.generateToken(userModel)).thenReturn(expectedToken);

        // Act
        String actualToken = jwtAdapter.generateToken(userModel);

        // Assert
        assertEquals(expectedToken, actualToken);
        verify(jwtProvider).generateToken(userModel);
    }

    @Test
    void testValidatePassword_MatchingPasswords_ReturnsTrue() {
        // Arrange
        when(passwordEncoder.matches("rawPassword", "encryptedPassword")).thenReturn(true);

        // Act
        boolean result = jwtAdapter.validatePassword("rawPassword", "encryptedPassword");

        // Assert
        assertTrue(result);
        verify(passwordEncoder).matches("rawPassword", "encryptedPassword");
    }

    @Test
    void testValidatePassword_NonMatchingPasswords_ReturnsFalse() {
        // Arrange
        when(passwordEncoder.matches("wrongPassword", "encryptedPassword")).thenReturn(false);

        // Act
        boolean result = jwtAdapter.validatePassword("wrongPassword", "encryptedPassword");

        // Assert
        assertFalse(result);
        verify(passwordEncoder).matches("wrongPassword", "encryptedPassword");
    }
}
