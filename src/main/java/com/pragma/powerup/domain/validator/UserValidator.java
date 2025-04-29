package com.pragma.powerup.domain.validator;

import com.pragma.powerup.domain.exception.DomainException;
import com.pragma.powerup.domain.model.UserModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class UserValidator {

    public void validateUser(UserModel userModel) {

        if (!isValidPhone(userModel.getPhone())) {
            throw new DomainException("Número de teléfono inválido.");
        }

        if (!isAdult(userModel.getBirthdate())) {
            throw new DomainException("Debe ser mayor de edad.");
        }
    }

    private boolean isValidPhone(String phone) {
        return phone.length() <= 13 && phone.matches("^\\+?\\d{1,13}$");
    }

    private boolean isAdult(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }
}
