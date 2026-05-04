package com.eams.user.mapper;

import com.eams.user.dto.LoginResponseDTO;
import com.eams.user.dto.RegisterRequestDTO;
import com.eams.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequestDTO dto);

    @Mapping(target = "password", ignore = true)
    LoginResponseDTO toDTO(User user);
}
