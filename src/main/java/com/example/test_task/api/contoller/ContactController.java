package com.example.test_task.api.contoller;

import com.example.test_task.api.annotation.IntegrationData;
import com.example.test_task.api.dto.business.ContactDto;
import com.example.test_task.api.dto.request.ContactChangeRequest;
import com.example.test_task.api.dto.request.ContactUpdateRequest;
import com.example.test_task.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
@Tag(name = "Contact Controller", description = "Handles operations for phone data and email data")
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/save")
    @Operation(summary = "Add new contacts for a user")
    public List<ContactDto> addContacts(@RequestBody @Valid List<ContactChangeRequest> requestList,
                                        @IntegrationData Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        return contactService.addContacts(requestList, userId);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete existing contacts for a user")
    public void deleteContacts(@RequestBody @Valid List<ContactChangeRequest> requestList,
                               @IntegrationData Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        contactService.deleteContacts(requestList, userId);
    }

    @PutMapping("/update")
    @Operation(summary = "Update existing contacts for a user")
    public List<ContactDto> updateContacts(@RequestBody @Valid List<ContactUpdateRequest> requestList,
                                           @IntegrationData Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        return contactService.updateContacts(requestList, userId);
    }
}
