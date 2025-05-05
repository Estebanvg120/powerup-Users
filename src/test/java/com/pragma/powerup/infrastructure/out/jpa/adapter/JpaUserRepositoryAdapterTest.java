package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaUserRepositoryAdapterTest {

    private IUserRepository userRepository;
    private IUserEntityMapper userEntityMapper;
    private JpaUserRepositoryAdapter jpaUserRepositoryAdapter;

    private UserModel userModel;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userRepository = mock(IUserRepository.class);
        userEntityMapper = mock(IUserEntityMapper.class);
        jpaUserRepositoryAdapter = new JpaUserRepositoryAdapter(userRepository, userEntityMapper);

        userModel = new UserModel();
        userEntity = new UserEntity();
    }

    @Test
    void testSave_ReturnsUserModel() {
        // Arrange
        when(userEntityMapper.toEntity(userModel)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userEntityMapper.toUserModel(userEntity)).thenReturn(userModel);

        // Act
        UserModel result = jpaUserRepositoryAdapter.save(userModel);

        // Assert
        assertNotNull(result);
        assertEquals(userModel, result);
        verify(userEntityMapper).toEntity(userModel);
        verify(userRepository).save(userEntity);
        verify(userEntityMapper).toUserModel(userEntity);
    }

    @Test
    void testFindByEmail_UserFound() {
        // Arrange
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUserModel(userEntity)).thenReturn(userModel);

        // Act
        Optional<UserModel> result = jpaUserRepositoryAdapter.findByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userModel, result.get());
        verify(userRepository).findByEmail(email);
        verify(userEntityMapper).toUserModel(userEntity);
    }

    @Test
    void testFindByEmail_UserNotFound() {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        Optional<UserModel> result = jpaUserRepositoryAdapter.findByEmail(email);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository).findByEmail(email);
        verify(userEntityMapper, never()).toUserModel(any());
    }

    @Test
    void testFindById_UserFound() {
        // Arrange
        long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userEntityMapper.toUserModel(userEntity)).thenReturn(userModel);

        // Act
        Optional<UserModel> result = jpaUserRepositoryAdapter.findById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userModel, result.get());
        verify(userRepository).findById(userId);
        verify(userEntityMapper).toUserModel(userEntity);
    }

    @Test
    void testFindById_UserNotFound() {
        // Arrange
        long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<UserModel> result = jpaUserRepositoryAdapter.findById(userId);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository).findById(userId);
        verify(userEntityMapper, never()).toUserModel(any());
    }
}