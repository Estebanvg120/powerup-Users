package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.UserRequestDto;
import com.pragma.powerup.domain.usecase.UserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.application.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServicePort userService;

    public UserController(UserServicePort userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponseDto> createUser (@Validated @RequestBody UserRequestDto userRequestDto){
        UserModel user = new UserModel(
                null,
                userRequestDto.getName(),
                userRequestDto.getLastname(),
                userRequestDto.getDocument(),
                userRequestDto.getPhone(),
                userRequestDto.getBirthdate(),
                userRequestDto.getEmail(),
                userRequestDto.getPassword(),
                userRequestDto.getRole()
        );
        UserModel createdUser = userService.createUser(user);
        UserResponseDto response = new UserResponseDto(
                createdUser.getId(),
                createdUser.getName(),
                createdUser.getLastname(),
                createdUser.getDocument(),
                createdUser.getPhone(),
                createdUser.getBirthdate(),
                createdUser.getEmail(),
                createdUser.getPassword(),
                createdUser.getRole()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
