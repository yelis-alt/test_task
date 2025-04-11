package com.example.test_task.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserFilterRequest {
    @Schema(description = "Date of birth",
            example = "71111112131")
    private LocalDate dateOfBirth;

    @Schema(description = "Phone",
            example = "71111112131")
    private String phone;

    @Schema(description = "Name",
            example = "Иванов Иван Иванович")
    private String name;

    @Schema(description = "Email",
            example = "ivam@yan.ru")
    private String email;
}
