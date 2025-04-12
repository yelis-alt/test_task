package com.example.test_task.persistence.mapper;

import com.example.test_task.api.dto.business.UserDto;
import com.example.test_task.api.dto.projection.UserProjection;
import com.example.test_task.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    UserDto toDto(UserEntity entity);

    UserDto toDto(UserProjection projection);
}
