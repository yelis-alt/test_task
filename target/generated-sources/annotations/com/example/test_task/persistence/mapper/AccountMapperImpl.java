package com.example.test_task.persistence.mapper;

import com.example.test_task.dto.business.AccountDto;
import com.example.test_task.dto.business.UserDto;
import com.example.test_task.persistence.entity.AccountEntity;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-11T19:06:35+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDto toDto(AccountEntity accountEntity) {
        if ( accountEntity == null ) {
            return null;
        }

        Long id = null;
        BigDecimal initialBalance = null;
        BigDecimal balance = null;

        id = accountEntity.getId();
        initialBalance = accountEntity.getInitialBalance();
        balance = accountEntity.getBalance();

        UserDto userDto = null;

        AccountDto accountDto = new AccountDto( id, userDto, initialBalance, balance );

        return accountDto;
    }
}
