package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.UserRequestDto;
import com.pragma.powerup.application.dto.UserResponseDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.mapper.usermapper.IUserResponseMapper;
import com.pragma.powerup.domain.exception.NoDataFoundException;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.infrastructure.input.rest.privateroutes.users.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private IUserHandler userHandler;

    @Mock
    private IUserResponseMapper responseMapper;

    @InjectMocks
    private UserController userController;

    private UserRequestDto userRequestDto;
    private UserModel userModel;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userRequestDto = new UserRequestDto();
        userModel = new UserModel();
        userResponseDto = new UserResponseDto();
    }

    @Test
    void testCreateUser_ReturnsCreatedResponse() {
        // Arrange
        when(userHandler.createUser(userRequestDto)).thenReturn(userModel);
        when(responseMapper.toResponse(userModel)).thenReturn(userResponseDto);

        // Act
        ResponseEntity<UserResponseDto> response = userController.createUser(userRequestDto);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(userResponseDto, response.getBody());

        verify(userHandler, times(1)).createUser(userRequestDto);
        verify(responseMapper, times(1)).toResponse(userModel);
    }

    @Test
    void testGetUserByID_UserFound_ReturnsCreatedResponse() {
        // Arrange
        long userId = 1L;
        when(userHandler.getUserByID(userId)).thenReturn(Optional.of(userModel));
        when(responseMapper.toResponse(userModel)).thenReturn(userResponseDto);

        // Act
        ResponseEntity<UserResponseDto> response = userController.getUserByID(userId);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(userResponseDto, response.getBody());

        verify(userHandler).getUserByID(userId);
        verify(responseMapper).toResponse(userModel);
    }

    @Test
    void testGetUserByID_UserNotFound_ThrowsException() {
        // Arrange
        long userId = 999L;
        when(userHandler.getUserByID(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoDataFoundException.class, () -> userController.getUserByID(userId));
        verify(userHandler).getUserByID(userId);
        verify(responseMapper, never()).toResponse(any());
    }
}