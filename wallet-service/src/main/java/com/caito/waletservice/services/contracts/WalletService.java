package com.caito.waletservice.services.contracts;

import com.caito.waletservice.api.models.requests.DepositRequest;
import com.caito.waletservice.api.models.requests.TransferRequest;
import com.caito.waletservice.api.models.requests.WalletRequest;
import com.caito.waletservice.api.models.responses.TransferResponse;
import com.caito.waletservice.api.models.responses.WalletResponse;
import com.pp.commonsservice.enums.WalletStatus;

import java.util.concurrent.CompletableFuture;

/**
 * Service interface for Wallet operations.
 *
 * @author caito
 *
 */
public interface WalletService {
    CompletableFuture<WalletResponse> createWallet(WalletRequest request);
    CompletableFuture<WalletResponse> getWalletById(Long walletId);
    CompletableFuture<WalletResponse> changeStatus(Long walletId, WalletStatus status);
    CompletableFuture<WalletResponse> deposit(DepositRequest request);
    CompletableFuture<TransferResponse> transfer(TransferRequest request);
}
