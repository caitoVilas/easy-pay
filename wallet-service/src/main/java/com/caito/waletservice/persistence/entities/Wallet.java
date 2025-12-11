package com.caito.waletservice.persistence.entities;

import com.pp.commonsservice.enums.Currency;
import com.pp.commonsservice.enums.UserType;
import com.pp.commonsservice.enums.WalletStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entity representing a Wallet.
 *
 * @author caito
 *
 */
@Entity
@Table(name = "wallets")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal availableBalance;
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal blockedBalance;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private WalletStatus walletStatus;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @Version
    private Long version;
}
