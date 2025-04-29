package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserRepositoryPort;
import com.pragma.powerup.domain.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServicePortTest {

    @Mock
    private IUserRepositoryPort userRepositoryPort;

    @Mock
    private UserValidator userValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServicePort userServicePort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldValidateEncryptAndSaveUser() {
        // Arrange
        UserModel userModel = new UserModel();
        userModel.setPassword("plainPassword");

        UserModel savedUserModel = new UserModel();
        savedUserModel.setId(1L);
        savedUserModel.setPassword("encryptedPassword");

        when(passwordEncoder.encode("plainPassword")).thenReturn("encryptedPassword");
        when(userRepositoryPort.save(any(UserModel.class))).thenReturn(savedUserModel);

        // Act
        UserModel result = userServicePort.createUser(userModel);

        // Assert
        verify(userValidator, times(1)).validateUser(userModel);
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userRepositoryPort, times(1)).save(userModel);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("encryptedPassword", result.getPassword());
    }
}