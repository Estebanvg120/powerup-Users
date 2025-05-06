package com.pragma.powerup.infrastructure.out.springsecurity;

import com.pragma.powerup.domain.spi.ISecurityContextPort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextAdapter implements ISecurityContextPort {

    private final PasswordEncoder passwordEncoder;

    public SecurityContextAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encryptedPassword(String password) {
        return passwordEncoder.encode(password);
    }


    public String getAuthenticatedRole() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
