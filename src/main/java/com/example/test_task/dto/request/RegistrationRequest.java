package com.example.test_task.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class RegistrationRequest {
    @NotBlank
    private String name;
    @NotBlank
    private LocalDate dateOfBirth;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @NotBlank
    private String password;
}
