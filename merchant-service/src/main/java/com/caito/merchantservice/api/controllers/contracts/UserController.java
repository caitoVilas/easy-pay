package com.caito.merchantservice.api.controllers.contracts;

import com.caito.merchantservice.api.models.requests.MerchantUserRequest;
import com.caito.merchantservice.api.models.responses.MerchantUserResponse;
import com.pp.commonsservice.models.ErrorResponse;
import com.pp.commonsservice.models.ErrorsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * UserController is the interface for user-related API endpoints.
 *
 * @author Caito
 *
 */
public interface UserController {
    @PostMapping("/register")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Register a User")
    @Parameters({
            @Parameter(name = "merchantId", description = "Merchant ID"),
            @Parameter(name = "request", description = "User data")
    })

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<MerchantUserResponse> createUser(@RequestParam Long merchantId,
                                                           @RequestBody MerchantUserRequest request);
}
