package com.caito.waletservice.services.impl;

import com.caito.waletservice.api.exceptios.customs.WalletException;
import com.caito.waletservice.api.models.requests.DepositRequest;
import com.caito.waletservice.api.models.requests.TransferRequest;
import com.caito.waletservice.api.models.requests.WalletRequest;
import com.caito.waletservice.api.models.responses.TransferResponse;
import com.caito.waletservice.api.models.responses.WalletResponse;
import com.caito.waletservice.persistence.entities.Wallet;
import com.caito.waletservice.persistence.repositories.TransactionRepository;
import com.caito.waletservice.persistence.repositories.WalletRepository;
import com.caito.waletservice.services.contracts.TransactionService;
import com.caito.waletservice.services.contracts.WalletService;
import com.caito.waletservice.utils.mappers.TransactionMapper;
import com.caito.waletservice.utils.mappers.WalletMapper;
import com.pp.commonsservice.enums.WalletStatus;
import com.pp.commonsservice.exceptions.NotFoundException;
import com.pp.commonsservice.logs.WriteLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the WalletService interface.
 *
 * @author caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final RestTemplate restTemplate;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    @Value("${base-url-merchant}")
    private String baseUrlMerchant;

    /**
     * Creates a new wallet based on the provided request.
     *
     * @param request the wallet creation request
     * @return a CompletableFuture containing the created WalletResponse
     */
    @Override
    @Transactional
    public CompletableFuture<WalletResponse> createWallet(WalletRequest request) {
        log.info(WriteLog.logInfo("--> Create wallet"));
        return CompletableFuture.supplyAsync(() ->{
            Wallet wallet = Wallet.builder()
                    .userId(request.getUserId())
                    .userType(request.getUserType())
                    .balance(BigDecimal.ZERO)
                    .availableBalance(BigDecimal.ZERO)
                    .blockedBalance(BigDecimal.ZERO)
                    .currency(request.getCurrency())
                    .walletStatus(WalletStatus.PENDING)
                    .build();
            Wallet savedWallet = walletRepository.save(wallet);
            log.info(WriteLog.logInfo("Wallet created with id: " + savedWallet.getId()));
            return WalletMapper.mapToDto(savedWallet);
        });
    }

    /**
     * Retrieves a wallet by its ID.
     *
     * @param walletId the ID of the wallet to retrieve
     * @return a CompletableFuture containing the WalletResponse
     * @throws NotFoundException if the wallet is not found
     */
    @Override
    public CompletableFuture<WalletResponse> getWalletById(Long walletId) {
        log.info(WriteLog.logInfo("--> Get wallet by id: " + walletId));

        return CompletableFuture.supplyAsync(() -> {
            Wallet wallet = walletRepository.findById(walletId)
                    .orElseThrow(() -> {
                        log.error(WriteLog.logError("--> Wallet not found with id: " + walletId));
                        return new NotFoundException("Wallet not found with id: " + walletId);
                    });
            log.info(WriteLog.logInfo("Wallet found with id: " + wallet.getId()));
            return WalletMapper.mapToDto(wallet);
        });
    }

    /**
     * Changes the status of a wallet.
     *
     * @param walletId the ID of the wallet to update
     * @param status the new status to set
     * @return a CompletableFuture containing the updated WalletResponse
     * @throws NotFoundException if the wallet is not found
     */
    @Override
    @Transactional
    public CompletableFuture<WalletResponse> changeStatus(Long walletId, WalletStatus status) {
        log.info(WriteLog.logInfo("--> Change wallet status: " + walletId));
        return CompletableFuture.supplyAsync(() -> {
            var wallet = getWalletById(walletId).join();
            var updatedWalled = WalletMapper.mapToEntity(wallet);
            updatedWalled.setWalletStatus(status);
            var updatedWallet = walletRepository.save(updatedWalled);
            log.info(WriteLog.logInfo("Wallet status changed for id: " + updatedWallet.getId()));
            return WalletMapper.mapToDto(updatedWallet);
        });
    }

        /**
        * Deposits an amount into a wallet.
        *
        * @param request the deposit request containing user ID and amount
        * @return a CompletableFuture containing the updated WalletResponse
        * @throws NotFoundException if the wallet is not found
        * @throws WalletException if the wallet is not active or the amount is invalid
        */
    @Override
    @Transactional
    public CompletableFuture<WalletResponse> deposit(DepositRequest request) {
        log.info(WriteLog.logInfo("--> Deposit service"));
        return CompletableFuture.supplyAsync(() -> {
            try {
                var wallet = walletRepository.findById(request.getUserId()).orElseThrow(() -> {
                    log.error(WriteLog.logError("--> Wallet not found with id: " + request.getUserId()));
                    return new NotFoundException("Wallet not found with id: " + request.getUserId());
                });
                validateStatus(wallet);
                validateAmount(request.getAmount());
                BigDecimal balanceBefore = wallet.getBalance();
                BigDecimal balanceAfter = balanceBefore.add(request.getAmount());
                wallet.setBalance(balanceAfter);
                wallet.setAvailableBalance(wallet.getAvailableBalance().add(request.getAmount()));
                Wallet updatedWallet = walletRepository.save(wallet);
                var trasaction = transactionService.transactionDeposit(updatedWallet, request.getAmount(),
                        balanceBefore);
                transactionRepository.save(TransactionMapper.mapToEntity(trasaction));
                return WalletMapper.mapToDto(updatedWallet);
            }catch (Exception e) {
                log.error(WriteLog.logError("Error during deposit: " + e.getMessage()));
                throw new WalletException("Error during deposit: " + e.getMessage());
            }
        });
    }

    /**
     * Transfers an amount from one wallet to another.
     *
     * @param request the transfer request containing source and destination user IDs and amount
     * @return a CompletableFuture containing the TransferResponse
     * @throws NotFoundException if either wallet is not found
     * @throws WalletException if either wallet is not active, the amount is invalid, or insufficient balance
     */
    @Override
    @Transactional
    public CompletableFuture<TransferResponse> transfer(TransferRequest request) {
        log.info(WriteLog.logInfo("--> Transfer service"));
        return CompletableFuture.supplyAsync(() -> {
            try {
                var fromWallet = walletRepository.findById(request.getFromUserId()).orElseThrow(() -> {
                    log.error(WriteLog.logError("--> Wallet not found with id: " + request.getFromUserId()));
                    return new NotFoundException("Wallet not found with id: " + request.getFromUserId());
                });
                var toWallet = walletRepository.findById(request.getToUserId()).orElseThrow(() -> {
                    log.error(WriteLog.logError("--> Wallet not found with id: " + request.getToUserId()));
                    return new NotFoundException("Wallet not found with id: " + request.getToUserId());
                });
                validateStatus(fromWallet);
                validateStatus(toWallet);
                validateAmount(request.getAmount());
                if (fromWallet.getBalance().compareTo(request.getAmount()) < 0) {
                    log.error(WriteLog.logError("--> Insufficient balance"));
                    throw new WalletException("Insufficient balance");
                }
                //debit source wallet
                BigDecimal fromBalanceBefore = fromWallet.getBalance();
                BigDecimal fromBalanceAfter = fromBalanceBefore.subtract(request.getAmount());
                fromWallet.setBalance(fromBalanceAfter);
                fromWallet.setAvailableBalance(fromWallet.getAvailableBalance().subtract(request.getAmount()));
                //credit destination wallet
                BigDecimal toBalanceBefore = toWallet.getBalance();
                BigDecimal toBalanceAfter = toBalanceBefore.add(request.getAmount());
                toWallet.setBalance(toBalanceAfter);
                toWallet.setAvailableBalance(toWallet.getAvailableBalance().add(request.getAmount()));
                walletRepository.save(fromWallet);
                walletRepository.save(toWallet);
                //record transactions
                var transacion = transactionService.transactionTransfer(fromWallet, toWallet, request.getAmount(),
                        fromBalanceBefore, toBalanceBefore);
                transactionRepository.save(TransactionMapper.mapToEntity(transacion.getOutTransaction()));
                transactionRepository.save(TransactionMapper.mapToEntity(transacion.getInTransaction()));
                return TransferResponse.builder()
                        .result("OK")
                        .currency(fromWallet.getCurrency())
                        .amount(request.getAmount())
                        .outReference(transacion.getOutTransaction().getTransactionReference())
                        .inReference(transacion.getInTransaction().getTransactionReference())
                        .build();
            }catch (Exception e) {
                log.error(WriteLog.logError("Error during transfer: " + e.getMessage()));
                throw new WalletException("Error during transfer: " + e.getMessage());
            }
        });
    }

    /*
     * Validates that the wallet is active.
     *
     * @param wallet the wallet to validate
     * @throws WalletException if the wallet is not active
     */
    private void validateStatus(Wallet wallet) {
        if (wallet.getWalletStatus() != WalletStatus.ACTIVE) {
            log.error(WriteLog.logError("--> Wallet is not active: " + wallet.getId()));
            throw new WalletException("Wallet is not active");
        }
    }

    /*
     *  Validates that the amount is greater than zero.
     *
     * @param amount the amount to validate
     * @throws WalletException if the amount is not greater than zero
     */
    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            log.error(WriteLog.logError("--> Invalid amount: " + amount));
            throw new WalletException("Amount must be greater than zero");
        }
    }
}
