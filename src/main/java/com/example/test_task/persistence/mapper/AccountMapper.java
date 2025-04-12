package com.example.test_task.persistence.mapper;

import com.example.test_task.api.dto.business.AccountDto;
import com.example.test_task.persistence.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(AccountEntity accountEntity);
}
