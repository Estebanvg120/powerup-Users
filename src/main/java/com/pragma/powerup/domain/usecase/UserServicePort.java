package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.validator.UserValidator;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.spi.IUserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServicePort implements IUserServicePort {
    private final IUserRepositoryPort userRepositoryPort;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    public UserServicePort(IUserRepositoryPort userRepositoryPort, UserValidator userValidator, PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserModel createUser(UserModel userModel) {
        userValidator.validateUser(userModel);
        String passEncrypted = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(passEncrypted);
        return userRepositoryPort.save(userModel);
    }
}
