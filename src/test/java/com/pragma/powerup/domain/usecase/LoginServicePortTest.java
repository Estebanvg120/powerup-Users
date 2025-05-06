package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.IncorrectPasswordException;
import com.pragma.powerup.domain.model.LoginModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IJwtPort;
import com.pragma.powerup.domain.spi.IUserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LoginServicePortTest {

    private IUserRepositoryPort userRepositoryPort;
    private IJwtPort jwtPort;
    private LoginServicePort loginServicePort;

    private LoginModel loginModel;
    private UserModel userModel;

    @BeforeEach
    void setUp() {
        userRepositoryPort = mock(IUserRepositoryPort.class);
        jwtPort = mock(IJwtPort.class);
        loginServicePort = new LoginServicePort(userRepositoryPort, jwtPort);

        loginModel = new LoginModel();
        loginModel.setEmail("test@example.com");
        loginModel.setPassword("rawPassword");

        userModel = new UserModel();
        userModel.setEmail("test@example.com");
        userModel.setPassword("hashedPassword");
    }

    @Test
    void testLogin_Success() {
        // Arrange
        when(userRepositoryPort.findByEmail(loginModel.getEmail())).thenReturn(Optional.of(userModel));
        when(jwtPort.validatePassword("rawPassword", "hashedPassword")).thenReturn(true);
        when(jwtPort.generateToken(userModel)).thenReturn("mockedToken");

        // Act
        String result = loginServicePort.login(loginModel);

        // Assert
        assertEquals("mockedToken", result);
        verify(userRepositoryPort).findByEmail("test@example.com");
        verify(jwtPort).validatePassword("rawPassword", "hashedPassword");
        verify(jwtPort).generateToken(userModel);
    }

    @Test
    void testLogin_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepositoryPort.findByEmail(loginModel.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        IncorrectPasswordException exception = assertThrows(
                IncorrectPasswordException.class,
                () -> loginServicePort.login(loginModel)
        );

        assertEquals("Usuario no encontrado", exception.getMessage());
        verify(userRepositoryPort).findByEmail("test@example.com");
        verify(jwtPort, never()).validatePassword(any(), any());
        verify(jwtPort, never()).generateToken(any());
    }

    @Test
    void testLogin_InvalidPassword_ThrowsException() {
        // Arrange
        when(userRepositoryPort.findByEmail(loginModel.getEmail())).thenReturn(Optional.of(userModel));
        when(jwtPort.validatePassword("rawPassword", "hashedPassword")).thenReturn(false);

        // Act & Assert
        IncorrectPasswordException exception = assertThrows(
                IncorrectPasswordException.class,
                () -> loginServicePort.login(loginModel)
        );

        assertEquals("Contraseña incorrecta", exception.getMessage());
        verify(userRepositoryPort).findByEmail("test@example.com");
        verify(jwtPort).validatePassword("rawPassword", "hashedPassword");
        verify(jwtPort, never()).generateToken(any());
    }
}
