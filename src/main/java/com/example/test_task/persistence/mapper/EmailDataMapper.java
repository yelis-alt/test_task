package com.example.test_task.persistence.mapper;

import com.example.test_task.api.dto.business.EmailDataDto;
import com.example.test_task.persistence.entity.EmailDataEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailDataMapper {
    EmailDataDto toDto(EmailDataEntity emailDataEntity);
}
