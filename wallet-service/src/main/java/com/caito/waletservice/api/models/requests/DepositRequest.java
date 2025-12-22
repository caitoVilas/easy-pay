package com.caito.waletservice.api.models.requests;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Request model for depositing funds into a user's wallet.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class DepositRequest implements Serializable {
    private Long userId;
    private BigDecimal amount;
    private String description;
}
