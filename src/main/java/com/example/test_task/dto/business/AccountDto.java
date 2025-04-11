package com.example.test_task.dto.business;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountDto {
    private Long id;

    private UserDto userDto;

    private BigDecimal initialBalance;

    private BigDecimal balance;
}
