package com.caito.merchantservice.api.controllers.impl;

import com.caito.merchantservice.api.controllers.contracts.UserController;
import com.caito.merchantservice.api.models.requests.CreatePasswordRequest;
import com.caito.merchantservice.api.models.requests.MerchantUserRequest;
import com.caito.merchantservice.api.models.requests.MerchantUserUpdateRequest;
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

    @Override
    public ResponseEntity<MerchantUserResponse> getUserById(Long userId, Long merchantId) {
        return ResponseEntity.ok(userService.getUserById(userId, merchantId));
    }

    @Override
    public ResponseEntity<MerchantUserResponse> updateUser(Long userId, MerchantUserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    @Override
    public ResponseEntity<Void> createPassword(CreatePasswordRequest request) {
        userService.createPassword(request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId, Long merchantId) {
        userService.deleteUser(userId, merchantId);
        return ResponseEntity.noContent().build();
    }
}
