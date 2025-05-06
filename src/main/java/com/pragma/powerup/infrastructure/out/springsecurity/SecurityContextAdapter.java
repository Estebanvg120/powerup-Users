package com.pragma.powerup.infrastructure.out.springsecurity;

import com.pragma.powerup.domain.spi.ISecurityContextPort;
import com.pragma.powerup.infrastructure.exception.NoTokenFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SecurityContextAdapter implements ISecurityContextPort {

    private final PasswordEncoder passwordEncoder;
    private final HttpServletRequest request;

    public SecurityContextAdapter(PasswordEncoder passwordEncoder, HttpServletRequest request) {
        this.passwordEncoder = passwordEncoder;
        this.request = request;
    }

    @Override
    public String encryptedPassword(String password) {
        return passwordEncoder.encode(password);
    }


    public String getAuthenticatedRole() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public String getToken() {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer " )) {
            throw new NoTokenFoundException("No se encontró el token en la cabecera");
        }
        return authHeader.substring(7);
    }

}
