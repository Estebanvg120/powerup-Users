package com.pragma.powerup.infrastructure.input.rest.privateroutes.users;

import com.pragma.powerup.application.dto.LoginRequestDto;
import com.pragma.powerup.application.dto.UserRequestDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.mapper.usermapper.IUserResponseMapper;
import com.pragma.powerup.domain.exception.NoDataFoundException;
import com.pragma.powerup.domain.usecase.UserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.application.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserHandler userHandler;
    private final IUserResponseMapper responseMapper;

    public UserController(IUserHandler userHandler, IUserResponseMapper responseMapper) {
        this.userHandler = userHandler;
        this.responseMapper = responseMapper;
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserResponseDto> createUser (@Validated @RequestBody UserRequestDto userRequestDto){
        UserModel createdUser = userHandler.createUser(userRequestDto);
        return new ResponseEntity<>(responseMapper.toResponse(createdUser), HttpStatus.CREATED);
    }

    @GetMapping("/userbyid")
    public ResponseEntity<UserResponseDto> getUserByID (@RequestParam long userid){
        Optional<UserModel> findUser = userHandler.getUserByID(userid);
        if(findUser.isPresent()){
            return new ResponseEntity<>(responseMapper.toResponse(findUser.get()), HttpStatus.CREATED);
        }else{throw new NoDataFoundException("El usuario no fue encontrado");
        }

    }

}
