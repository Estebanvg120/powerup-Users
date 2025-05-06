package com.pragma.powerup.infrastructure.out.springsecurity;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityContextAdapterTest {

    private PasswordEncoder passwordEncoder;
    private SecurityContextAdapter securityContextAdapter;

    @BeforeEach
    void setUp() {
        passwordEncoder = mock(PasswordEncoder.class);
        securityContextAdapter = new SecurityContextAdapter(passwordEncoder);
    }

    @Test
    void testEncryptedPassword_ReturnsEncoded() {
        // Arrange
        String rawPassword = "myPassword";
        String encoded = "encodedPassword";
        when(passwordEncoder.encode(rawPassword)).thenReturn(encoded);

        // Act
        String result = securityContextAdapter.encryptedPassword(rawPassword);

        // Assert
        assertEquals(encoded, result);
        verify(passwordEncoder).encode(rawPassword);
    }

    @Test
    void testGetAuthenticatedRole_ReturnsPrincipalAsString() {
        try (MockedStatic<SecurityContextHolder> mocked = mockStatic(SecurityContextHolder.class)) {
            Authentication authentication = mock(Authentication.class);
            SecurityContext context = mock(SecurityContext.class);
            when(context.getAuthentication()).thenReturn(authentication);
            when(authentication.getPrincipal()).thenReturn("authenticatedRole");

            mocked.when(SecurityContextHolder::getContext).thenReturn(context);

            String role = securityContextAdapter.getAuthenticatedRole();

            assertEquals("authenticatedRole", role);
        }
    }
}