package com.caito.merchantservice.api.controllers.impl;

import com.caito.merchantservice.api.controllers.contracts.MerchantController;
import com.caito.merchantservice.api.models.requests.MerchantRequest;
import com.caito.merchantservice.api.models.responses.MerchantResponse;
import com.caito.merchantservice.services.contracts.MerchantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
