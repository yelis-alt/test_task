package com.example.test_task.contoller;

import com.example.test_task.dto.business.UserDto;
import com.example.test_task.dto.request.LoginRequest;
import com.example.test_task.dto.request.RegistrationRequest;
import com.example.test_task.service.AuthorizationService;
import com.example.test_task.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization")
@Tag(name = "Authorization Controller", description = "Handles operations for user's authorization")
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @Operation(summary = "Register a user")
    public UserDto register(@RequestBody @Valid RegistrationRequest request) {
        return authorizationService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        return authorizationService.authenticate(request)
                .map(user -> {
                    String token = jwtService.generateToken(user.getId());
                    return ResponseEntity.ok().body("JWT: " + token);
                })
                .orElse(ResponseEntity.status(401).body("Invalid credentials"));
    }
}
