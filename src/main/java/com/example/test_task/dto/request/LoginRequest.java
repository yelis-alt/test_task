package com.example.test_task.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    private String email;
    private String phone;
    @NotBlank
    private String password;
}
