package com.caito.waletservice.services.impl;

import com.caito.waletservice.api.models.responses.TransactionDto;
import com.caito.waletservice.api.models.responses.TransferDto;
import com.caito.waletservice.persistence.entities.Wallet;
import com.caito.waletservice.services.contracts.TransactionService;
import com.pp.commonsservice.enums.TransactionStatus;
import com.pp.commonsservice.enums.TransactionType;
import com.pp.commonsservice.logs.WriteLog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementation of the TransactionService interface.
 *
 * @author caito
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransctionServiceImpl implements TransactionService {

    /**
     * Records a deposit transaction for the specified wallet and amount.
     *
     * @param wallet the wallet to which the deposit is made
     * @param amount the amount deposited
     */
    @Override
    public TransactionDto transactionDeposit(Wallet wallet, BigDecimal amount, BigDecimal balanceBefore) {
        log.info("--> Transaction deposit");
        TransactionDto transaction = TransactionDto.builder()
                .walletId(wallet.getId())
                .transactionReference(generateTransactionRef())
                .transactionType(TransactionType.DEPOSIT)
                .amount(amount)
                .balanceBefore(balanceBefore)
                .balanceAfter(wallet.getBalance())
                .currency(wallet.getCurrency())
                .status(TransactionStatus.COMPLETED)
                .description("Deposit transaction")
                .carriedOut(LocalDateTime.now())
                .build();
        log.info("Deposit transaction recorded with reference: {}", transaction.getTransactionReference());
        return transaction;
    }

    /**
     * Records a transfer transaction between two wallets.
     *
     * @param walletFrom the wallet from which the amount is transferred
     * @param walletTo   the wallet to which the amount is transferred
     * @param amount     the amount transferred
     */
    @Override
    @Transactional
    public TransferDto transactionTransfer(Wallet walletFrom, Wallet walletTo, BigDecimal amount,
                                           BigDecimal balanceBeforeFrom, BigDecimal balanceBeforeTo) {
        log.info(WriteLog.logInfo("--> Transaction transfer"));
        var outTransaction = TransactionDto.builder()
                .walletId(walletFrom.getId())
                .transactionReference(generateTransactionRef()+"-OUT")
                .transactionType(TransactionType.TRANSFER_OUT)
                .amount(amount)
                .balanceBefore(balanceBeforeFrom)
                .balanceAfter(walletFrom.getBalance())
                .currency(walletFrom.getCurrency())
                .status(TransactionStatus.COMPLETED)
                .description("Transfer out transaction")
                .carriedOut(LocalDateTime.now())
                .relatedWalletId(walletTo.getId())
                .build();
        var inTransaction = TransactionDto.builder()
                .walletId(walletTo.getId())
                .transactionReference(generateTransactionRef()+"-IN")
                .transactionType(TransactionType.TRANSFER_IN)
                .amount(amount)
                .balanceBefore(balanceBeforeTo)
                .balanceAfter(walletTo.getBalance())
                .currency(walletTo.getCurrency())
                .status(TransactionStatus.COMPLETED)
                .description("Transfer in transaction")
                .carriedOut(LocalDateTime.now())
                .relatedWalletId(walletFrom.getId())
                .build();
        log.info(WriteLog.logInfo("Transfer transactions recorded with references: "+
                outTransaction.getTransactionReference() + " and " + inTransaction.getTransactionReference()));
        return TransferDto.builder()
                .outTransaction(outTransaction)
                .inTransaction(inTransaction)
                .build();
    }

    /**
     * Generates a unique transaction reference.
     *
     * @return a unique transaction reference string
     */
    private String generateTransactionRef() {
        return "TXN-".concat(UUID.randomUUID().toString().substring(0, 18).toUpperCase());
    }
}
