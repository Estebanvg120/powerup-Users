package com.pragma.powerup.domain.spi;
import com.pragma.powerup.domain.model.UserModel;

public interface IJwtPort {
    String generateToken(UserModel userModel);
    Boolean validatePassword(String loginPassword, String userPassword);
    String getRoleFromToken (String token);
}
