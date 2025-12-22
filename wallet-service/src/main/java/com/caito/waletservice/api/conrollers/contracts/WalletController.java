package com.caito.waletservice.api.conrollers.contracts;

import com.caito.waletservice.api.models.requests.DepositRequest;
import com.caito.waletservice.api.models.requests.TransferRequest;
import com.caito.waletservice.api.models.requests.WalletRequest;
import com.caito.waletservice.api.models.responses.TransferResponse;
import com.caito.waletservice.api.models.responses.WalletResponse;
import com.pp.commonsservice.enums.WalletStatus;
import com.pp.commonsservice.models.ErrorResponse;
import com.pp.commonsservice.models.ErrorsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

public interface WalletController {
    @PostMapping("/create")
    @Operation(summary = "Create a Wallet")
    @Parameter(name = "request", description = "data to create wallet")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Walles dreated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CompletableFuture<WalletResponse> createWallet(@RequestBody WalletRequest request);

    @GetMapping("/{id}")
    @Operation(summary = "Get Wallet by Id")
    @Parameter(name = "id", description = "wallet Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Wallet found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CompletableFuture<WalletResponse> getWalletById(@PathVariable Long id);

    @PutMapping("/{id}/status/{status}")
    @Operation(summary = "Change Wallet Status")
    @Parameters({
            @Parameter(name = "id", description = "wallet Id"),
            @Parameter(name = "status", description = "new wallet status")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Wallet status changed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CompletableFuture<WalletResponse> changeStatus(@PathVariable Long id, @PathVariable WalletStatus status);

    @PostMapping("/deposit")
    @Operation(summary = "Deposit amount to Wallet")
    @Parameter(name = "request", description = "data to deposit")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deposit successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CompletableFuture<WalletResponse> deposit(@RequestBody DepositRequest request);

    @PostMapping("/transfer")
    @Operation(summary = "transfer amount between Wallets")
    @Parameter(name = "request", description = "data to transfer")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transfer successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CompletableFuture<TransferResponse> transfer(@RequestBody TransferRequest request);
}
