package com.pragma.powerup.domain.validator;

import com.pragma.powerup.domain.exception.IncorrectPasswordException;
import com.pragma.powerup.domain.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private UserValidator userValidator;

    @BeforeEach
    void setUp() {
        userValidator = new UserValidator();
    }

    @Test
    void validateUser_shouldPass_whenValidUser() {
        // Arrange
        UserModel user = new UserModel();
        user.setPhone("+573001112233");
        user.setBirthdate(LocalDate.of(1995, 5, 5));

        // Act & Assert
        assertDoesNotThrow(() -> userValidator.validateUser(user));
    }

    @Test
    void validateUser_shouldThrowException_whenInvalidPhone() {
        // Arrange
        UserModel user = new UserModel();
        user.setPhone("123abc");  // Invalid phone
        user.setBirthdate(LocalDate.of(1990, 1, 1));

        // Act & Assert
        IncorrectPasswordException exception = assertThrows(IncorrectPasswordException.class,
                () -> userValidator.validateUser(user));
        assertEquals("Número de teléfono inválido.", exception.getMessage());
    }

    @Test
    void validateUser_shouldThrowException_whenUnderage() {
        // Arrange
        UserModel user = new UserModel();
        user.setPhone("+573001112233");
        user.setBirthdate(LocalDate.now().minusYears(17));  // Not an adult

        // Act & Assert
        IncorrectPasswordException exception = assertThrows(IncorrectPasswordException.class,
                () -> userValidator.validateUser(user));
        assertEquals("Debe ser mayor de edad.", exception.getMessage());
    }
}