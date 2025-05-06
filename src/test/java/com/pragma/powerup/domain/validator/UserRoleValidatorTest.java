package com.pragma.powerup.domain.validator;

import com.pragma.powerup.domain.exception.InvalidRoleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleValidatorTest {

    private UserRoleValidator userRoleValidator;

    @BeforeEach
    void setUp() {
        userRoleValidator = new UserRoleValidator();
    }

    @Test
    void testAdministradorCanCreatePropietario() {
        assertDoesNotThrow(() -> userRoleValidator.validateRole("ADMINISTRADOR", "PROPIETARIO"));
    }

    @Test
    void testPropietarioCannotCreatePropietario() {
        InvalidRoleException exception = assertThrows(
                InvalidRoleException.class,
                () -> userRoleValidator.validateRole("PROPIETARIO", "PROPIETARIO")
        );
        assertEquals("Un usuario PROPIETARIO solo puede ser creado por un usuario ADMINISTRADOR", exception.getMessage());
    }

    @Test
    void testPropietarioCanCreateEmpleado() {
        assertDoesNotThrow(() -> userRoleValidator.validateRole("PROPIETARIO", "EMPLEADO"));
    }

    @Test
    void testAdministradorCannotCreateEmpleado() {
        InvalidRoleException exception = assertThrows(
                InvalidRoleException.class,
                () -> userRoleValidator.validateRole("ADMINISTRADOR", "EMPLEADO")
        );
        assertEquals("Un usuario EMPLEADO solo puede ser creado por un usuario PROPIETARIO", exception.getMessage());
    }

    @Test
    void testAnyoneCanCreateCliente() {
        assertDoesNotThrow(() -> userRoleValidator.validateRole("ADMINISTRADOR", "CLIENTE"));
        assertDoesNotThrow(() -> userRoleValidator.validateRole("PROPIETARIO", "CLIENTE"));
        assertDoesNotThrow(() -> userRoleValidator.validateRole("EMPLEADO", "CLIENTE"));
        assertDoesNotThrow(() -> userRoleValidator.validateRole("CLIENTE", "CLIENTE"));
    }

    @Test
    void testInvalidRoleThrowsException() {
        InvalidRoleException exception = assertThrows(
                InvalidRoleException.class,
                () -> userRoleValidator.validateRole("ADMINISTRADOR", "GERENTE")
        );
        assertEquals("Rol no valido", exception.getMessage());
    }
}