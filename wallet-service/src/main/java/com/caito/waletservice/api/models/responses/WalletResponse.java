package com.caito.waletservice.api.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pp.commonsservice.enums.Currency;
import com.pp.commonsservice.enums.UserType;
import com.pp.commonsservice.enums.WalletStatus;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class WalletResponse implements Serializable {
    private Long id;
    private Long userId;
    private UserType userType;
    private Currency currency;
    private WalletStatus walletStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00")
    private BigDecimal balance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00")
    private BigDecimal availableBalance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "#,##0.00")
    private BigDecimal blockedBalance;
}
