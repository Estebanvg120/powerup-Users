package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.UserRequestDto;
import com.pragma.powerup.application.mapper.IUserRequestMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class UserHandlerTest {

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @InjectMocks
    private UserHandler userHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldCallServiceAndMapperCorrectly() {
        // Arrange
        UserRequestDto userRequestDto = new UserRequestDto();
        // Aquí puedes setear algunos valores si quieres
        UserModel userModel = new UserModel();

        when(userRequestMapper.toObject(userRequestDto)).thenReturn(userModel);

        // Act
        userHandler.createUser(userRequestDto);

        // Assert
        verify(userRequestMapper, times(1)).toObject(userRequestDto);
        verify(userServicePort, times(1)).createUser(userModel);
    }
}