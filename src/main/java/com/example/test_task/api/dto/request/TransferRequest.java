package com.example.test_task.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class TransferRequest {
    @NotNull
    @Schema(description = "user_id who receives money",
            example = "PHONE")
    private Long toUserId;

    @NotNull
    @Schema(description = "Amount of money to be transferred to ",
            example = "21.12")
    private Double value;
}
