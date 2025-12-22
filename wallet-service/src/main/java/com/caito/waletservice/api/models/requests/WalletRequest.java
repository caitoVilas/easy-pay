package com.caito.waletservice.api.models.requests;

import com.pp.commonsservice.enums.Currency;
import com.pp.commonsservice.enums.UserType;
import lombok.*;

import java.io.Serializable;

/**
 * Request model for creating a Wallet.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class WalletRequest implements Serializable {
    private Long userId;
    private UserType userType;
    private Currency currency;
}
