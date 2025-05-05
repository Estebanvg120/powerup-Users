package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.ILoginPort;
import com.pragma.powerup.domain.exception.IncorrectPasswordException;
import com.pragma.powerup.domain.model.LoginModel;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IJwtPort;
import com.pragma.powerup.domain.spi.IUserRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class LoginServicePort implements ILoginPort {

    private final IUserRepositoryPort userRepositoryPort;
    private final IJwtPort jwtPort;

    public LoginServicePort(IUserRepositoryPort userRepositoryPort, IJwtPort jwtPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.jwtPort = jwtPort;
    }

    @Override
    public String login(LoginModel loginModel) {
        UserModel userModel = userRepositoryPort.findByEmail(loginModel.getEmail()).orElseThrow(() -> new IncorrectPasswordException("Usuario no encontrado"));
        if(!jwtPort.validatePassword(loginModel.getPassword(), userModel.getPassword())) throw new IncorrectPasswordException("Contraseña incorrecta");
        return jwtPort.generateToken(userModel);

    }
}
