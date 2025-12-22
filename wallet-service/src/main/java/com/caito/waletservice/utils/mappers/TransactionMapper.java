package com.caito.waletservice.utils.mappers;

import com.caito.waletservice.api.models.responses.TransactionDto;
import com.caito.waletservice.persistence.entities.Transaction;

/**
 * Mapper class for converting TransactionDto to Transaction entity.
 *
 * @author caito
 *
 */
public class TransactionMapper {

    /**
     * Maps a TransactionDto to a Transaction entity.
     *
     * @param dto the TransactionDto to be mapped
     * @return the corresponding Transaction entity
     */
    public static Transaction mapToEntity(TransactionDto dto){
        return Transaction.builder()
                .walletId(dto.getWalletId())
                .transactionReference(dto.getTransactionReference())
                .transactionType(dto.getTransactionType())
                .amount(dto.getAmount())
                .balanceBefore(dto.getBalanceBefore())
                .balanceAfter(dto.getBalanceAfter())
                .currency(dto.getCurrency())
                .status(dto.getStatus())
                .description(dto.getDescription())
                .relatedWalletId(dto.getRelatedWalletId())
                .carriedOut(dto.getCarriedOut())
                .build();
    }
}
