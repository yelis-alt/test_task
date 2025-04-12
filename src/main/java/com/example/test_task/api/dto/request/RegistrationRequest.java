package com.example.test_task.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class RegistrationRequest {
    @NotBlank
    private String name;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @Size(min = 8, max = 500, message = "Password must be between 8 and 500 characters")
    private String password;
}
