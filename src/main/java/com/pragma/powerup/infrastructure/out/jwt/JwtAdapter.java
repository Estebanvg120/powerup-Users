package com.pragma.powerup.infrastructure.out.jwt;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IJwtPort;
import com.pragma.powerup.infrastructure.config.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtAdapter implements IJwtPort {

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public JwtAdapter(PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public String generateToken(UserModel userModel) {
        return jwtProvider.generateToken(userModel);
    }

    @Override
    public Boolean validatePassword(String loginPassword, String userPassword) {
        return passwordEncoder.matches(loginPassword, userPassword);
    }
}
