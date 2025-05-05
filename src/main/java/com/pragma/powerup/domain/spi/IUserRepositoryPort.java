package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.UserModel;

import java.util.Optional;

public interface IUserRepositoryPort {
    UserModel save(UserModel userModel);

    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findById(long userid);
}
