package com.example.test_task.persistence.mapper;

import com.example.test_task.dto.business.PhoneDataDto;
import com.example.test_task.persistence.entity.PhoneDataEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneDataMapper {
    PhoneDataDto toDto(PhoneDataEntity phoneDataEntity);
}
