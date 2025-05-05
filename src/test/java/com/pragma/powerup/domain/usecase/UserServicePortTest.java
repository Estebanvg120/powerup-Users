package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.AlreadyUserExistException;
import com.pragma.powerup.domain.exception.NoDataFoundException;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.ISecurityContextPort;
import com.pragma.powerup.domain.spi.IUserRepositoryPort;
import com.pragma.powerup.domain.validator.UserRoleValidator;
import com.pragma.powerup.domain.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServicePortTest {

    private IUserRepositoryPort userRepositoryPort;
    private UserValidator userValidator;
    private ISecurityContextPort securityContextPort;
    private UserRoleValidator userRoleValidator;
    private UserServicePort userServicePort;

    @BeforeEach
    void setUp() {
        userRepositoryPort = mock(IUserRepositoryPort.class);
        userValidator = mock(UserValidator.class);
        securityContextPort = mock(ISecurityContextPort.class);
        userRoleValidator = mock(UserRoleValidator.class);
        userServicePort = new UserServicePort(userRepositoryPort, userValidator, securityContextPort, userRoleValidator);
    }

    @Test
    void createUser_shouldSaveUser_whenValid() {
        // Arrange
        UserModel user = new UserModel();
        user.setEmail("test@example.com");
        user.setPassword("1234");
        user.setRole("CLIENT");

        when(securityContextPort.getAuthenticatedRole()).thenReturn("ADMIN");
        when(securityContextPort.encryptedPassword("1234")).thenReturn("encrypted1234");
        when(userRepositoryPort.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepositoryPort.save(any(UserModel.class))).thenReturn(user);

        // Act
        UserModel result = userServicePort.createUser(user);

        // Assert
        assertEquals("test@example.com", result.getEmail());
        verify(userValidator).validateUser(user);
        verify(userRoleValidator).validateRole("ADMIN", "CLIENT");
    }

    @Test
    void createUser_shouldThrowException_whenUserAlreadyExists() {
        // Arrange
        UserModel user = new UserModel();
        user.setEmail("existing@example.com");
        user.setPassword("pass");
        user.setRole("CLIENT");

        when(securityContextPort.getAuthenticatedRole()).thenReturn("ADMIN");
        when(userRepositoryPort.findByEmail("existing@example.com"))
                .thenReturn(Optional.of(user));

        // Act & Assert
        assertThrows(AlreadyUserExistException.class, () -> userServicePort.createUser(user));
    }

    @Test
    void getUserById_shouldReturnUser_whenExists() {
        // Arrange
        UserModel user = new UserModel();
        user.setId(1L);
        user.setEmail("user@example.com");

        when(userRepositoryPort.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Optional<UserModel> result = userServicePort.getUserById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("user@example.com", result.get().getEmail());
    }

    @Test
    void getUserById_shouldThrowException_whenNotFound() {
        // Arrange
        when(userRepositoryPort.findById(2L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoDataFoundException.class, () -> userServicePort.getUserById(2L));
    }
}