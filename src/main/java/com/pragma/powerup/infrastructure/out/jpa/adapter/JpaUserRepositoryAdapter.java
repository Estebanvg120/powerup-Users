package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserRepositoryPort;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaUserRepositoryAdapter implements IUserRepositoryPort {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;

    public JpaUserRepositoryAdapter(IUserRepository userRepository, IUserEntityMapper userEntityMapper) {
        this.userRepository = userRepository;
        this.userEntityMapper = userEntityMapper;
    }

    @Override
    public UserModel save(UserModel userModel) {
        UserEntity userEntity = userRepository.save(userEntityMapper.toEntity(userModel));
        return userEntityMapper.toUserModel(userEntity);
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return userRepository.findByEmail(email).map(userEntityMapper::toUserModel);
    }

    public Optional<UserModel> findById(long userid) {
        return userRepository.findById(userid).map(userEntityMapper::toUserModel);
    }
}
