package com.caito.merchantservice.api.controllers.impl;

import com.caito.merchantservice.api.controllers.contracts.MerchantController;
import com.caito.merchantservice.api.models.requests.MerchantRequest;
import com.caito.merchantservice.api.models.requests.MerchantUpdateRequest;
import com.caito.merchantservice.api.models.responses.MerchantResponse;
import com.caito.merchantservice.api.models.responses.MerchantUserResponse;
import com.caito.merchantservice.services.contracts.MerchantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * MerchantControllerImpl
 *
 * Implements the MerchantController interface to handle HTTP requests related to merchants.
 */
@RestController
@RequestMapping("/v1/merchants")
@RequiredArgsConstructor
@Tag(name = "Merchant API", description = "Endpoints for managing merchants")
@CrossOrigin(origins = "*")
public class MerchantControllerImpl implements MerchantController {
    private final MerchantService merchantService;

    @Override
    public ResponseEntity<MerchantResponse> createMerchant(MerchantRequest request) {
        return ResponseEntity.status(HttpStatus.SC_CREATED).body(merchantService.createMerchant(request));
    }

    @Override
    public ResponseEntity<List<MerchantResponse>> getAllMerchants() {
        var merchants = merchantService.getAllMerchants();
        if (merchants.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(merchants);
    }

    @Override
    public ResponseEntity<List<MerchantUserResponse>> getAllUsers(Long merchantId) {
        var users = merchantService.getAllUsersbyMerchantId(merchantId);
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<MerchantResponse> getMerchantById(Long id) {
        return ResponseEntity.ok(merchantService.getMerchantById(id));
    }

    @Override
    public ResponseEntity<MerchantResponse> updateMerchant(Long id, MerchantUpdateRequest request) {
        return ResponseEntity.ok(merchantService.updateMerchant(id, request));
    }

    @Override
    public ResponseEntity<?> deleteMerchant(Long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.noContent().build();
    }
}
