package com.caito.waletservice.persistence.entities;

import com.pp.commonsservice.enums.Currency;
import com.pp.commonsservice.enums.TransactionStatus;
import com.pp.commonsservice.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a financial transaction associated with a wallet.
 * Includes details such as transaction type, amount, currency, status, and timestamps.
 *
 * @author caito
 *
 */
@Entity
@Table(name = "transactions")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
