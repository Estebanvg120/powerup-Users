package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.LoginRequestDto;
import com.pragma.powerup.application.handler.ILoginHandler;
import com.pragma.powerup.application.mapper.loginmapper.ILoginRequestMapper;
import com.pragma.powerup.domain.api.ILoginPort;
import com.pragma.powerup.domain.model.LoginModel;
import org.springframework.stereotype.Component;

@Component
public class LoginHandler implements ILoginHandler {

    private final ILoginPort loginPort;
    private final ILoginRequestMapper loginRequestMapper;

    public LoginHandler(ILoginPort loginPort, ILoginRequestMapper loginRequestMapper) {
        this.loginPort = loginPort;
        this.loginRequestMapper = loginRequestMapper;
    }

    @Override
    public String login(LoginRequestDto loginRequestDto) {
        LoginModel loginModel = loginRequestMapper.toLoginModel(loginRequestDto);
        return loginPort.login(loginModel);
    }
}
