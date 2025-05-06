package com.pragma.powerup.infrastructure.out.springsecurity;

import com.pragma.powerup.infrastructure.exception.NoTokenFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityContextAdapterTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpServletRequest request;

    @Mock
    private SecurityContextAdapter securityContextAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        securityContextAdapter = new SecurityContextAdapter(passwordEncoder, request);
    }

    @Test
    void testEncryptedPassword_ReturnsEncodedPassword() {
        // Arrange
        String rawPassword = "password123";
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        // Act
        String result = securityContextAdapter.encryptedPassword(rawPassword);

        // Assert
        assertEquals(encodedPassword, result);
        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    void testGetAuthenticatedRole_ReturnsAuthenticatedRole() {
        // Arrange
        String expectedRole = "ROLE_USER";
        SecurityContextAdapter spyAdapter = spy(securityContextAdapter);
        SecurityContextHolder.getContext().setAuthentication(mock(Authentication.class));
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(expectedRole);

        // Act
        String role = spyAdapter.getAuthenticatedRole();

        // Assert
        assertEquals(expectedRole, role);
    }

    @Test
    void testGetToken_ReturnsToken() {
        // Arrange
        String expectedToken = "mockedToken";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + expectedToken);

        // Act
        String token = securityContextAdapter.getToken();

        // Assert
        assertEquals(expectedToken, token);
    }

    @Test
    void testGetToken_ThrowsNoTokenFoundException_WhenNoTokenFound() {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act & Assert
        NoTokenFoundException exception = assertThrows(NoTokenFoundException.class, () -> securityContextAdapter.getToken());
        assertEquals("No se encontró el token en la cabecera", exception.getMessage());
    }

    @Test
    void testGetToken_ThrowsNoTokenFoundException_WhenInvalidTokenFormat() {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("InvalidToken");

        // Act & Assert
        NoTokenFoundException exception = assertThrows(NoTokenFoundException.class, () -> securityContextAdapter.getToken());
        assertEquals("No se encontró el token en la cabecera", exception.getMessage());
    }
}