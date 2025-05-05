package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.UserRequestDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.mapper.usermapper.IUserRequestMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserHandler implements IUserHandler {
    private final IUserServicePort userServicePort;
    private final IUserRequestMapper userRequestMapper;

    public UserHandler(IUserServicePort userServicePort, IUserRequestMapper userRequestMapper) {
        this.userServicePort = userServicePort;
        this.userRequestMapper = userRequestMapper;
    }

    @Override
    public UserModel createUser(UserRequestDto userRequestDto) {
        UserModel userModel = userRequestMapper.toObject(userRequestDto);
        return userServicePort.createUser(userModel);
    }

    @Override
    public Optional<UserModel> getUserByID(long userid) {
    return userServicePort.getUserById(userid);
    }
}
