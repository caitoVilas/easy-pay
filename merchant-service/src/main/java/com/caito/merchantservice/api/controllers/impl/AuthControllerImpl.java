package com.caito.merchantservice.api.controllers.impl;

import com.caito.merchantservice.api.controllers.contracts.AuthController;
import com.caito.merchantservice.api.models.requests.AuthRequest;
import com.caito.merchantservice.api.models.responses.AuthResponse;
import com.caito.merchantservice.services.contracts.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthControllerImpl implements the AuthController interface to handle
 * authentication-related HTTP requests.
 * It uses AuthService to perform the login operation.
 *
 * @author caito
 *
 */
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "Endpoints for user authentication")
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    public ResponseEntity<AuthResponse> login(AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
