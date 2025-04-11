package com.example.test_task.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferRequest {
    @NotNull
    @Schema(description = "user_id who receives money",
            example = "PHONE")
    private Long toUserId;

    @NotBlank
    @Schema(description = "Amount of money to be transferred to ",
            example = "21.12")
    private Double value;
}
