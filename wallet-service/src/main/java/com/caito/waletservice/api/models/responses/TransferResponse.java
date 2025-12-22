package com.caito.waletservice.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pp.commonsservice.enums.Currency;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Response model for transfer operations.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class TransferResponse implements Serializable {
    private String result;
    private Currency currency;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00")
    private BigDecimal amount;
    private String outReference;
    private String inReference;
}
