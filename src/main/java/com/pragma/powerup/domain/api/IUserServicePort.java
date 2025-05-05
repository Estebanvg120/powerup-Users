package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.UserModel;

import java.util.Optional;

public interface IUserServicePort {
    UserModel createUser(UserModel userModel);
    Optional<UserModel> getUserById(long iduser);
}
