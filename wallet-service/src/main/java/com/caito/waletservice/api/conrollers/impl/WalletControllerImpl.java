package com.caito.waletservice.api.conrollers.impl;

import com.caito.waletservice.api.conrollers.contracts.WalletController;
import com.caito.waletservice.api.models.requests.DepositRequest;
import com.caito.waletservice.api.models.requests.TransferRequest;
import com.caito.waletservice.api.models.requests.WalletRequest;
import com.caito.waletservice.api.models.responses.TransferResponse;
import com.caito.waletservice.api.models.responses.WalletResponse;
import com.caito.waletservice.services.contracts.WalletService;
import com.pp.commonsservice.enums.WalletStatus;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the WalletController interface.
 * This class handles HTTP requests related to Wallet operations.
 *
 * @author caito
 *
 */
@RestController
@RequestMapping("/v1/wallets")
@RequiredArgsConstructor
@Tag(name = "Wallet API", description = "Endpoints for Wallet operations")
public class WalletControllerImpl implements WalletController {
    private final WalletService walletService;

    @Override
    public CompletableFuture<WalletResponse> createWallet(WalletRequest request) {
        var res =  walletService.createWallet(request);
        return res.thenApply(walletResponse ->
                ResponseEntity.status(HttpStatus.CREATED).body(walletResponse).getBody());
    }

    @Override
    public CompletableFuture<WalletResponse> getWalletById(Long id) {
        var res = walletService.getWalletById(id);
        return res.thenApply(response ->
                ResponseEntity.status(HttpStatus.OK).body(response).getBody());
    }

    @Override
    public CompletableFuture<WalletResponse> changeStatus(Long id, WalletStatus status) {
        var res = walletService.changeStatus(id, status);
        return res.thenApply(response ->
                ResponseEntity.status(HttpStatus.OK).body(response).getBody());
    }

    @Override
    public CompletableFuture<WalletResponse> deposit(DepositRequest request) {
        var res = walletService.deposit(request);
        return res.thenApply(response ->
                ResponseEntity.status(HttpStatus.OK).body(response).getBody());
    }

    @Override
    public CompletableFuture<TransferResponse> transfer(TransferRequest request) {
        var res = walletService.transfer(request);
        return res.thenApply(response ->
                ResponseEntity.status(HttpStatus.OK).body(response).getBody());
    }
}
