package com.pragma.powerup.infrastructure.input.rest.publicroutes.users;

import com.pragma.powerup.application.dto.UserRequestDto;
import com.pragma.powerup.application.dto.UserResponseDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.mapper.usermapper.IUserResponseMapper;
import com.pragma.powerup.domain.model.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/userspublic")
public class UserControllerPublic {
    private final IUserHandler userHandler;
    private final IUserResponseMapper responseMapper;

    public UserControllerPublic(IUserHandler userHandler, IUserResponseMapper responseMapper) {
        this.userHandler = userHandler;
        this.responseMapper = responseMapper;
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponseDto> createUser (@Validated @RequestBody UserRequestDto userRequestDto){
        UserModel createdUser = userHandler.createUser(userRequestDto);
        return new ResponseEntity<>(responseMapper.toResponse(createdUser), HttpStatus.CREATED);
    }
}
