package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.AlreadyUserExistException;
import com.pragma.powerup.domain.exception.NoDataFoundException;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IJwtPort;
import com.pragma.powerup.domain.spi.ISecurityContextPort;
import com.pragma.powerup.domain.validator.UserRoleValidator;
import com.pragma.powerup.domain.validator.UserValidator;
import com.pragma.powerup.domain.spi.IUserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServicePortTest {

    @Mock
    private IUserRepositoryPort userRepositoryPort;

    @Mock
    private UserValidator userValidator;

    @Mock
    private ISecurityContextPort securityContextPort;

    @Mock
    private UserRoleValidator userRoleValidator;

    @Mock
    private IJwtPort jwtPort;

    private UserServicePort userServicePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userServicePort = new UserServicePort(userRepositoryPort, userValidator, securityContextPort, userRoleValidator, jwtPort);
    }

    @Test
    void testCreateUser_UserAlreadyExists_ThrowsAlreadyUserExistException() {
        // Arrange
        UserModel userModel = new UserModel();
        userModel.setEmail("test@example.com");
        when(userRepositoryPort.findByEmail(userModel.getEmail())).thenReturn(Optional.of(userModel));

        // Act & Assert
        AlreadyUserExistException exception = assertThrows(AlreadyUserExistException.class, () -> userServicePort.createUser(userModel));
        assertEquals("EL usuario ya existe", exception.getMessage());
    }

    @Test
    void testCreateUser_SuccessfullyCreatesUser() {
        // Arrange
        UserModel userModel = new UserModel();
        userModel.setEmail("test@example.com");
        userModel.setRole("CLIENTE");
        when(userRepositoryPort.findByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(securityContextPort.getToken()).thenReturn("mockedToken");
        when(jwtPort.getRoleFromToken("mockedToken")).thenReturn("ADMINISTRADOR");
        when(securityContextPort.encryptedPassword(userModel.getPassword())).thenReturn("encryptedPassword");
        when(userRepositoryPort.save(userModel)).thenReturn(userModel);

        // Act
        UserModel createdUser = userServicePort.createUser(userModel);

        // Assert
        assertEquals(userModel, createdUser);
        verify(userRoleValidator).validateRole("ADMINISTRADOR", userModel.getRole());
        verify(userValidator).validateUser(userModel);
        verify(userRepositoryPort).save(userModel);
    }

    @Test
    void testGetUserById_UserNotFound_ThrowsNoDataFoundException() {
        // Arrange
        long userId = 1L;
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        NoDataFoundException exception = assertThrows(NoDataFoundException.class, () -> userServicePort.getUserById(userId));
        assertEquals("Usuario no existe", exception.getMessage());
    }

    @Test
    void testGetUserById_SuccessfullyReturnsUser() {
        // Arrange
        long userId = 1L;
        UserModel userModel = new UserModel();
        userModel.setId(userId);
        when(userRepositoryPort.findById(userId)).thenReturn(Optional.of(userModel));

        // Act
        Optional<UserModel> foundUser = userServicePort.getUserById(userId);

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals(userModel, foundUser.get());
    }
}