package com.pragma.powerup.application.mapper.loginmapper;

import com.pragma.powerup.application.dto.LoginResponseDto;
import com.pragma.powerup.domain.model.LoginModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface ILoginResponseMapper {
    LoginResponseDto toLoginResponse (LoginModel loginModel);
}
