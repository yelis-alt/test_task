package com.example.test_task.api.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {
    @Email
    private String email;
    @Pattern(
            regexp = "^7\\d{10}$",
            message = "Phone number must be 11 digits and start with 7"
    )
    private String phone;
    @Size(min = 8, max = 500, message = "Password must be between 8 and 500 characters")
    private String password;
}
