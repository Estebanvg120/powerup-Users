package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.UserRequestDto;
import com.pragma.powerup.application.mapper.usermapper.IUserRequestMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserHandlerTest {

    private IUserServicePort userServicePort;
    private IUserRequestMapper userRequestMapper;
    private UserHandler userHandler;

    @BeforeEach
    void setUp() {
        userServicePort = mock(IUserServicePort.class);
        userRequestMapper = mock(IUserRequestMapper.class);
        userHandler = new UserHandler(userServicePort, userRequestMapper);
    }

    @Test
    void testCreateUser() {
        // Given
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Esteban");
        dto.setEmail("esteban@test.com");

        UserModel mappedModel = new UserModel();
        mappedModel.setName("Esteban");
        mappedModel.setEmail("esteban@test.com");

        UserModel savedModel = new UserModel();
        savedModel.setId(1L);
        savedModel.setName("Esteban");
        savedModel.setEmail("esteban@test.com");

        when(userRequestMapper.toObject(dto)).thenReturn(mappedModel);
        when(userServicePort.createUser(mappedModel)).thenReturn(savedModel);

        // When
        UserModel result = userHandler.createUser(dto);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Esteban", result.getName());
        verify(userRequestMapper).toObject(dto);
        verify(userServicePort).createUser(mappedModel);
    }

    @Test
    void testGetUserById() {
        // Given
        long userId = 10L;
        UserModel userModel = new UserModel();
        userModel.setId(userId);
        userModel.setName("Test");

        when(userServicePort.getUserById(userId)).thenReturn(Optional.of(userModel));

        // When
        Optional<UserModel> result = userHandler.getUserByID(userId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        verify(userServicePort).getUserById(userId);
    }
}