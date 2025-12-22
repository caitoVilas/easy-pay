package com.caito.waletservice.api.models.requests;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Request model for transferring funds between wallets.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class TransferRequest implements Serializable {
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;
}
