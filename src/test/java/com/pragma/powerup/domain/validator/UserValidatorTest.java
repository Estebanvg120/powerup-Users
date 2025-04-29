package com.pragma.powerup.domain.validator;

import com.pragma.powerup.domain.exception.DomainException;
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
    void validateUser_ShouldNotThrow_WhenUserIsValid() {
        // Arrange
        UserModel validUser = new UserModel();
        validUser.setPhone("+1234567890");
        validUser.setBirthdate(LocalDate.now().minusYears(20)); // Tiene 20 años

        // Act & Assert
        assertDoesNotThrow(() -> userValidator.validateUser(validUser));
    }

    @Test
    void validateUser_ShouldThrow_WhenPhoneIsInvalid() {
        // Arrange
        UserModel invalidPhoneUser = new UserModel();
        invalidPhoneUser.setPhone("123-abc"); // Teléfono inválido
        invalidPhoneUser.setBirthdate(LocalDate.now().minusYears(25));

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> userValidator.validateUser(invalidPhoneUser));
        assertEquals("Número de teléfono inválido.", exception.getMessage());
    }

    @Test
    void validateUser_ShouldThrow_WhenUserIsMinor() {
        // Arrange
        UserModel minorUser = new UserModel();
        minorUser.setPhone("+1234567890");
        minorUser.setBirthdate(LocalDate.now().minusYears(17)); // 17 años, es menor de edad

        // Act & Assert
        DomainException exception = assertThrows(DomainException.class, () -> userValidator.validateUser(minorUser));
        assertEquals("Debe ser mayor de edad.", exception.getMessage());
    }
}