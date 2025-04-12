package com.example.test_task.api.dto.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
    private Long id;

    private String name;

    private LocalDate dateOfBirth;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AccountDto accountDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<EmailDataDto> emailDataDtoList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PhoneDataDto> phoneDataDtoList;
}
