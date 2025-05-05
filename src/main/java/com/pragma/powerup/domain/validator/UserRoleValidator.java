package com.pragma.powerup.domain.validator;

import com.pragma.powerup.domain.exception.InvalidRole;
import org.springframework.stereotype.Component;

@Component
public class UserRoleValidator {
    public void validateRole(String authenticatedRole, String createRole){
        switch (createRole){
            case "PROPIETARIO":
                if(!authenticatedRole.equals("ADMINISTRADOR")) throw new InvalidRole("Un usuario PROPIETARIO solo puede ser creado por un usuario ADMINISTRADOR");
                break;
            case "EMPLEADO":
                if(!authenticatedRole.equals("PROPIETARIO")) throw new InvalidRole("Un usuario EMPLEADO solo puede ser creado por un usuario PROPIETARIO");
                break;
            case "CLIENTE":
                if(!createRole.equals("CLIENTE")) throw new InvalidRole(("EL rol del usuario debe ser CLIENTE"));
                break;
            default:
                throw new InvalidRole("Rol no valido");
        }
    }
}
