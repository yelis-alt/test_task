package com.example.test_task.contoller;

import com.example.test_task.dto.business.AccountDto;
import com.example.test_task.dto.request.TransferRequest;
import com.example.test_task.service.FinanceService;
import com.example.test_task.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/finance")
@Tag(name = "Finance Controller", description = "Handles financial operations for users")
public class FinanceController {
    private final JwtService jwtService;
    private final FinanceService financeService;

    @PostMapping("/transfer")
    @Operation(summary = "Transfer money from account to another")
    public AccountDto addContacts(@RequestBody @Valid TransferRequest request,
                                  @RequestHeader("Authorization") String authorizationHeader) throws AuthenticationException {
        Long fromUserId = jwtService.getUserIdFromHeader(authorizationHeader);
        return financeService.transfer(request, fromUserId);
    }
}
