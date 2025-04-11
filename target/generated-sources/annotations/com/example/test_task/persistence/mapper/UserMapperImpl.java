package com.example.test_task.persistence.mapper;

import com.example.test_task.dto.business.UserDto;
import com.example.test_task.dto.projection.UserProjection;
import com.example.test_task.persistence.entity.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-11T19:06:35+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( entity.getId() );
        userDto.setName( entity.getName() );
        userDto.setDateOfBirth( entity.getDateOfBirth() );

        return userDto;
    }

    @Override
    public UserDto toDto(UserProjection projection) {
        if ( projection == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( projection.getId() );
        userDto.setName( projection.getName() );
        userDto.setDateOfBirth( projection.getDateOfBirth() );

        return userDto;
    }
}
