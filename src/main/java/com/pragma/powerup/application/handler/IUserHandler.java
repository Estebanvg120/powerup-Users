package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.UserRequestDto;
import com.pragma.powerup.domain.model.UserModel;

import java.util.Optional;

public interface IUserHandler {
    UserModel createUser(UserRequestDto userRequestDto);
    Optional<UserModel> getUserByID(long userid);
}
