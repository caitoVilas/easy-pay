package com.caito.waletservice.api.models.responses;

import com.pp.commonsservice.enums.Currency;
import com.pp.commonsservice.enums.TransactionStatus;
import com.pp.commonsservice.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a financial transaction deposit.
 * Includes details such as transaction type, amount, currency, status, and timestamps.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class TransactionDto implements Serializable {
    private Long walletId;
    private String transactionReference;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private BigDecimal amount;
    private BigDecimal balanceBefore;
    private BigDecimal balanceAfter;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private String description;
    private Long relatedWalletId;
    private LocalDateTime carriedOut;
}
