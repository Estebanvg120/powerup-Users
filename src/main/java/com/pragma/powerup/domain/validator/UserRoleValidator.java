package com.pragma.powerup.domain.validator;

import com.pragma.powerup.domain.exception.InvalidRoleException;
import org.springframework.stereotype.Component;

@Component
public class UserRoleValidator {
    public void validateRole(String authenticatedRole, String createRole){
        switch (createRole){
            case "PROPIETARIO":
                if(!authenticatedRole.equals("ADMINISTRADOR")) throw new InvalidRoleException("Un usuario PROPIETARIO solo puede ser creado por un usuario ADMINISTRADOR");
                break;
            case "EMPLEADO":
                if(!authenticatedRole.equals("PROPIETARIO")) throw new InvalidRoleException("Un usuario EMPLEADO solo puede ser creado por un usuario PROPIETARIO");
                break;
            case "CLIENTE":
                break;
            default:
                throw new InvalidRoleException("Rol no valido");
        }
    }
}
