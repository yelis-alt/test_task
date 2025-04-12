package com.example.test_task.persistence.mapper;

import com.example.test_task.api.dto.business.PhoneDataDto;
import com.example.test_task.persistence.entity.PhoneDataEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-12T15:43:38+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.26 (Amazon.com Inc.)"
)
@Component
public class PhoneDataMapperImpl implements PhoneDataMapper {

    @Override
    public PhoneDataDto toDto(PhoneDataEntity phoneDataEntity) {
        if ( phoneDataEntity == null ) {
            return null;
        }

        PhoneDataDto phoneDataDto = new PhoneDataDto();

        phoneDataDto.setPhone( phoneDataEntity.getPhone() );

        return phoneDataDto;
    }
}
