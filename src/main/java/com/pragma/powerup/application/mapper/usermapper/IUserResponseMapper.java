package com.pragma.powerup.application.mapper.usermapper;

import com.pragma.powerup.application.dto.UserResponseDto;
import com.pragma.powerup.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserResponseMapper {
    UserResponseDto toResponse(UserModel userModel);
}
