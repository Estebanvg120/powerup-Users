package com.pragma.powerup.infrastructure.input.rest.publicroutes.auth;

import com.pragma.powerup.application.dto.LoginRequestDto;
import com.pragma.powerup.application.handler.ILoginHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final ILoginHandler loginHandler;

    public AuthController(ILoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(loginHandler.login(loginRequestDto));
    }
}
