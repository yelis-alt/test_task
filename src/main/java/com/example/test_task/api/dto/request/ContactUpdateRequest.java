package com.example.test_task.api.dto.request;

import com.example.test_task.api.dto.enums.ContactTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
