package com.eams.user.mapper;

import com.eams.user.dto.RegisterRequestDTO;
import com.eams.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(RegisterRequestDTO dto);
}
