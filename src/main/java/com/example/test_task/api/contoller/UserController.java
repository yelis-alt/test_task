package com.example.test_task.api.contoller;

import com.example.test_task.api.annotation.IntegrationData;
import com.example.test_task.api.dto.business.UserDto;
import com.example.test_task.api.dto.request.UserFilterRequest;
import com.example.test_task.security.JwtService;
import com.example.test_task.service.FinanceService;
import com.example.test_task.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User Controller", description = "Handles user-related operations")
public class UserController {
    private UserService userService;

    @GetMapping("/get-with-filters")
    @Operation(summary = "Get users with filters")
    public Page<UserDto> getWithFilters(@RequestBody UserFilterRequest request,
                                        @RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        @IntegrationData Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        return userService.getWithFilters(request, page, size);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
