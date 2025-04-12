package com.example.test_task.persistence.mapper;

import com.example.test_task.api.dto.business.AccountDto;
import com.example.test_task.persistence.entity.AccountEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-12T15:43:38+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 11.0.26 (Amazon.com Inc.)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDto toDto(AccountEntity accountEntity) {
        if ( accountEntity == null ) {
            return null;
        }

        AccountDto accountDto = new AccountDto();

        accountDto.setId( accountEntity.getId() );
        accountDto.setUserId( accountEntity.getUserId() );
        accountDto.setInitialBalance( accountEntity.getInitialBalance() );
        accountDto.setBalance( accountEntity.getBalance() );

        return accountDto;
    }
}
