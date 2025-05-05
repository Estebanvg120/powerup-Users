package com.pragma.powerup.infrastructure.config;

import com.pragma.powerup.infrastructure.constants.Constants;
import com.pragma.powerup.infrastructure.exception.ExpiredTokenException;
import com.pragma.powerup.infrastructure.exception.NoTokenFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.equals(Constants.LOGIN_PATH) || path.equals(Constants.CREATE_USER_PUBLIC_PATH)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        try{

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtProvider.validateToken(token)) {
                String role = jwtProvider.getRoleFromToken(token);
                if(role != null){
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            role, null, jwtProvider.getAuthoritiesFromToken(token));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }else throw new ExpiredTokenException("Token Expirado");
        }else throw new NoTokenFoundException("No se encuentra token en el header");

        filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
