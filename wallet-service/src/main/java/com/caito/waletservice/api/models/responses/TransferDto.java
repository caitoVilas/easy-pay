package com.caito.waletservice.api.models.responses;

import lombok.*;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) representing a transfer consisting of an outgoing and incoming transaction.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class TransferDto implements Serializable {
    private TransactionDto outTransaction;
    private TransactionDto inTransaction;
}
