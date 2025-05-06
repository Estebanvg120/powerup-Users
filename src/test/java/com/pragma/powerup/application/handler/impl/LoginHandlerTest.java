package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.LoginRequestDto;
import com.pragma.powerup.application.mapper.loginmapper.ILoginRequestMapper;
import com.pragma.powerup.domain.api.ILoginPort;
import com.pragma.powerup.domain.model.LoginModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LoginHandlerTest {

    private ILoginPort loginPort;
    private ILoginRequestMapper loginRequestMapper;
    private LoginHandler loginHandler;

    @BeforeEach
    void setUp() {
        loginPort = mock(ILoginPort.class);
        loginRequestMapper = mock(ILoginRequestMapper.class);
        loginHandler = new LoginHandler(loginPort, loginRequestMapper);
    }

    @Test
    void testLogin_ReturnsToken() {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        LoginModel loginModel = new LoginModel();
        String expectedToken = "mocked-jwt-token";

        when(loginRequestMapper.toLoginModel(loginRequestDto)).thenReturn(loginModel);
        when(loginPort.login(loginModel)).thenReturn(expectedToken);

        // Act
        String result = loginHandler.login(loginRequestDto);

        // Assert
        assertEquals(expectedToken, result);
        verify(loginRequestMapper).toLoginModel(loginRequestDto);
        verify(loginPort).login(loginModel);
    }
}
