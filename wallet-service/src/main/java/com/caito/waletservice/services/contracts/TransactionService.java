package com.caito.waletservice.services.contracts;

import com.caito.waletservice.api.models.responses.TransactionDto;
import com.caito.waletservice.api.models.responses.TransferDto;
import com.caito.waletservice.persistence.entities.Wallet;

import java.math.BigDecimal;

/**
 * Service interface for Transaction operations.
 *
 * @author caito
 *
 */
public interface TransactionService {
    TransactionDto transactionDeposit(Wallet wallet, BigDecimal amount, BigDecimal balanceBefore);
    TransferDto transactionTransfer(Wallet walletFrom, Wallet walletTo, BigDecimal amount, BigDecimal balanceBeforeFrom,
                                    BigDecimal balanceBeforeTo);
}
