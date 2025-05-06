package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.AlreadyUserExistException;
import com.pragma.powerup.domain.exception.NoDataFoundException;
import com.pragma.powerup.domain.spi.IJwtPort;
import com.pragma.powerup.domain.spi.ISecurityContextPort;
import com.pragma.powerup.domain.validator.UserRoleValidator;
import com.pragma.powerup.domain.validator.UserValidator;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.spi.IUserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicePort implements IUserServicePort {
    private final IUserRepositoryPort userRepositoryPort;
    private final UserValidator userValidator;
    private final ISecurityContextPort securityContextPort;
    private final UserRoleValidator userRoleValidator;
    private final IJwtPort jwtPort;

    public UserServicePort(IUserRepositoryPort userRepositoryPort, UserValidator userValidator, ISecurityContextPort securityContextPort, UserRoleValidator userRoleValidator, IJwtPort jwtPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.userValidator = userValidator;
        this.securityContextPort = securityContextPort;
        this.userRoleValidator = userRoleValidator;
        this.jwtPort = jwtPort;
    }

    @Override
    public UserModel createUser(UserModel userModel) {
        String token = securityContextPort.getToken();
        String authenticatedRole = jwtPort.getRoleFromToken(token);
        userRoleValidator.validateRole(authenticatedRole, userModel.getRole());
        userValidator.validateUser(userModel);
        String passEncrypted = securityContextPort.encryptedPassword(userModel.getPassword());
        userModel.setPassword(passEncrypted);
        Optional<UserModel> findUser = userRepositoryPort.findByEmail(userModel.getEmail());
        if(findUser.isPresent()) throw new AlreadyUserExistException("EL usuario ya existe");
        return userRepositoryPort.save(userModel);
    }

    @Override
    public Optional<UserModel> getUserById(long iduser) {
        Optional<UserModel> userModel = userRepositoryPort.findById(iduser);
        if(userModel.isEmpty()) throw new NoDataFoundException("Usuario no existe");
        return userModel;
    }
}
