package com.example.test_task.api.contoller;

import com.example.test_task.security.CustomUserDetails;
import com.example.test_task.api.dto.business.UserDto;
import com.example.test_task.api.dto.request.LoginRequest;
import com.example.test_task.api.dto.request.RegistrationRequest;
import com.example.test_task.service.AuthorizationService;
import com.example.test_task.security.JwtService;
import com.example.test_task.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization")
@Tag(name = "Authorization Controller", description = "Handles operations for user's authorization")
public class AuthorizationController {
    private final AuthenticationManager authenticationManager;
    private final AuthorizationService authorizationService;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @Operation(summary = "Register a user")
    public UserDto register(@RequestBody @Valid RegistrationRequest request) {
        return authorizationService.register(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        try {
            String email;
            if (request.getEmail() == null) {
                email = userService.getEmailByPhone(request.getPhone());
            } else {
                email = request.getEmail();
            }

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, request.getPassword())
            );

            if (authentication != null
                && authentication.getPrincipal() instanceof CustomUserDetails
            ) {
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                final String jwt = jwtService.generateToken(userDetails.getId());
                return ResponseEntity.ok(jwt);
            }
        } catch (BadCredentialsException e) {
            log.error("Authorization error", e);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
    }
}
