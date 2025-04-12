package com.example.test_task.api.contoller;

import com.example.test_task.api.annotation.IntegrationData;
import com.example.test_task.api.dto.business.AccountDto;
import com.example.test_task.api.dto.request.TransferRequest;
import com.example.test_task.service.FinanceService;
import com.example.test_task.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/finance")
@Tag(name = "Finance Controller", description = "Handles financial operations for users")
public class FinanceController {
    private FinanceService financeService;

    @PostMapping("/transfer")
    @Operation(summary = "Transfer money from account to another")
    public AccountDto transfer(@RequestBody @Valid TransferRequest request,
                               @IntegrationData Long fromUserId) {
        if (fromUserId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        return financeService.transfer(request, fromUserId);
    }

    @Autowired
    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }
}
