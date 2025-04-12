package com.example.test_task.api.dto.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;

    private Long userId;

    private BigDecimal initialBalance;

    private BigDecimal balance;
}
