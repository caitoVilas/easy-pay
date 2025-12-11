package com.caito.merchantservice.api.controllers.contracts;

import com.caito.merchantservice.api.models.requests.CreatePasswordRequest;
import com.caito.merchantservice.api.models.requests.MerchantUserRequest;
import com.caito.merchantservice.api.models.requests.MerchantUserUpdateRequest;
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
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/users/{userId}/{merchantId}")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Get  users for a Merchant")
    @Parameters({
            @Parameter(name = "userId", description = "User ID"),
            @Parameter(name = "merchantId", description = "Merchant ID")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "No content",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<MerchantUserResponse> getUserById(@RequestParam Long userId,
                                                            @RequestParam Long merchantId);

    @PutMapping("/users/{userId}")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Get  users for a Merchant")
    @Parameters({
            @Parameter(name = "userId", description = "User ID"),
            @Parameter(name = "request", description = "User data to update")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "No content",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<MerchantUserResponse> updateUser(@RequestParam Long userId,
                                                           @RequestBody MerchantUserUpdateRequest request);

    @PostMapping("/create-password")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Create user Password")
    @Parameter(name = "request", description = "Password data")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password registered successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> createPassword(@RequestBody CreatePasswordRequest request);

    @DeleteMapping("/users/{userId}/{merchantId}")
    @SecurityRequirement(name = "security token")
    @Operation(summary = "Delete a User")
    @Parameters({
            @Parameter(name = "userId", description = "User ID"),
            @Parameter(name = "merchantId", description = "Merchant ID")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> deleteUser(@RequestParam Long userId,
                                           @RequestParam Long merchantId);
}
