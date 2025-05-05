package com.pragma.powerup.application.handler;

import com.pragma.powerup.application.dto.LoginRequestDto;

public interface ILoginHandler {
    String login(LoginRequestDto loginRequestDto);
}
