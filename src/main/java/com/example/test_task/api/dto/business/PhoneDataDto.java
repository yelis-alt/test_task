package com.example.test_task.api.dto.business;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PhoneDataDto extends ContactDto {
    private String phone;
}
