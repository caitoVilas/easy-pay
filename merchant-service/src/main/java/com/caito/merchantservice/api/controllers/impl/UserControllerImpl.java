package com.caito.merchantservice.api.controllers.impl;

import com.caito.merchantservice.api.controllers.contracts.UserController;
import com.caito.merchantservice.api.models.requests.MerchantUserRequest;
import com.caito.merchantservice.api.models.responses.MerchantUserResponse;
import com.caito.merchantservice.services.contracts.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * UserControllerImpl is the implementation of UserController for user-related API endpoints.
 *
 * @author Caito
 *
 */
@RestController
@RequestMapping("/merchants/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "Endpoints for managing users associated with merchants")
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<MerchantUserResponse> createUser(Long merchantId, MerchantUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(merchantId, request));
    }
}
