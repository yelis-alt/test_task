package com.example.test_task.api.dto.business;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EmailDataDto extends ContactDto {
    private String email;
}
