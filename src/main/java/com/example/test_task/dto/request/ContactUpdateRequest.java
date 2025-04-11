package com.example.test_task.dto.request;

import com.example.test_task.dto.enums.ContactTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ContactUpdateRequest {
    @NotNull
    @Schema(description = "Type of contact",
            example = "PHONE")
    private ContactTypeEnum contact;

    @NotBlank
    @Schema(description = "Value of old contact",
            example = "71111112131")
    private String oldValue;

    @NotBlank
    @Schema(description = "Value of new contact",
            example = "71111112132")
    private String newValue;
}
