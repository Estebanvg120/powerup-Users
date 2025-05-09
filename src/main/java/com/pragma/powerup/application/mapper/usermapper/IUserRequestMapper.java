package com.pragma.powerup.application.mapper.usermapper;

import com.pragma.powerup.application.dto.UserRequestDto;
import com.pragma.powerup.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserRequestMapper {
    UserModel toObject(UserRequestDto userRequestDto);
}
