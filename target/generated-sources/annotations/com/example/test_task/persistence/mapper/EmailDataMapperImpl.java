package com.example.test_task.persistence.mapper;

import com.example.test_task.dto.business.EmailDataDto;
import com.example.test_task.persistence.entity.EmailDataEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-11T22:01:07+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.26 (Amazon.com Inc.)"
)
@Component
public class EmailDataMapperImpl implements EmailDataMapper {

    @Override
    public EmailDataDto toDto(EmailDataEntity emailDataEntity) {
        if ( emailDataEntity == null ) {
            return null;
        }

        EmailDataDto emailDataDto = new EmailDataDto();

        emailDataDto.setEmail( emailDataEntity.getEmail() );

        return emailDataDto;
    }
}
