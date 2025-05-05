package com.pragma.powerup.application.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginRequestDto {

    @Email(message = "El correo no es válido.")
    @NotBlank(message = "El correo electrónico es obligatorio.")
    private String email;

    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
