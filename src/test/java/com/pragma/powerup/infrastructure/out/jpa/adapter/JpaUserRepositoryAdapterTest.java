package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class JpaUserRepositoryAdapterTest {

    private IUserRepository userRepository;
    private IUserEntityMapper userEntityMapper;
    private JpaUserRepositoryAdapter jpaUserRepositoryAdapter;

    @BeforeEach
    void setUp() {
        userRepository = mock(IUserRepository.class);
        userEntityMapper = mock(IUserEntityMapper.class);
        jpaUserRepositoryAdapter = new JpaUserRepositoryAdapter(userRepository, userEntityMapper);
    }

    @Test
    void save_ShouldSaveAndReturnUserModel() {
        // Arrange
        UserModel userModel = new UserModel();
        userModel.setName("Juan");
        userModel.setEmail("juan@example.com");

        UserEntity userEntity = new UserEntity();
        userEntity.setName("Juan");
        userEntity.setEmail("juan@example.com");

        when(userEntityMapper.toEntity(any(UserModel.class))).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userEntityMapper.toUserModel(any(UserEntity.class))).thenReturn(userModel);

        // Act
        UserModel result = jpaUserRepositoryAdapter.save(userModel);

        // Assert
        assertNotNull(result);
        assertEquals("Juan", result.getName());
        assertEquals("juan@example.com", result.getEmail());

        verify(userEntityMapper, times(1)).toEntity(any(UserModel.class));
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userEntityMapper, times(1)).toUserModel(any(UserEntity.class));
    }
}