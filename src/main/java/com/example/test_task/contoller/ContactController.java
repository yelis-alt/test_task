package com.example.test_task.contoller;

import com.example.test_task.dto.business.ContactDto;
import com.example.test_task.dto.request.ContactChangeRequest;
import com.example.test_task.dto.request.ContactUpdateRequest;
import com.example.test_task.service.ContactService;
import com.example.test_task.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contact")
@Tag(name = "Contact Controller", description = "Handles operations for phone data and email data")
public class ContactController {
    private final ContactService contactService;
    private final JwtService jwtService;

    @PostMapping("/save")
    @Operation(summary = "Add new contacts for a user")
    public List<ContactDto> addContacts(@RequestBody @Valid List<ContactChangeRequest> requestList,
                                        @RequestHeader("Authorization") String authorizationHeader) throws AuthenticationException {
        Long userId = jwtService.getUserIdFromHeader(authorizationHeader);
        return contactService.addContacts(requestList, userId);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete existing contacts for a user")
    public void deleteContacts(@RequestBody @Valid List<ContactChangeRequest> requestList,
                               @RequestHeader("Authorization") String authorizationHeader) throws AuthenticationException {
        Long userId = jwtService.getUserIdFromHeader(authorizationHeader);
        contactService.deleteContacts(requestList, userId);
    }

    @PutMapping("/update")
    @Operation(summary = "Update existing contacts for a user")
    public List<ContactDto> updateContacts(@RequestBody @Valid List<ContactUpdateRequest> requestList,
                                           @RequestHeader("Authorization") String authorizationHeader) throws AuthenticationException {
        Long userId = jwtService.getUserIdFromHeader(authorizationHeader);
        return contactService.updateContacts(requestList, userId);
    }
}
