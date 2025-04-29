package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.UserRequestDto;
import com.pragma.powerup.domain.model.UserModel;

public interface IUserHandler {
    void createUser(UserRequestDto userRequestDto);
}
