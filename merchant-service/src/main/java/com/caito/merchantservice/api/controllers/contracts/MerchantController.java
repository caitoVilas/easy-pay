package com.caito.merchantservice.api.controllers.contracts;

import com.caito.merchantservice.api.models.requests.MerchantRequest;
import com.caito.merchantservice.api.models.responses.MerchantResponse;
import com.pp.commonsservice.models.ErrorResponse;
import com.pp.commonsservice.models.ErrorsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/*
 * MerchantController is the interface for merchant-related API endpoints.
 *
 * @author Caito
 *
 */
public interface MerchantController {
    @PostMapping("/register")
    @Operation(summary = "Register a Merchant")
    @Parameter(name = "request", description = "Merchant & user admin data")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Merchant registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<MerchantResponse> createMerchant(@RequestBody MerchantRequest request);
}
