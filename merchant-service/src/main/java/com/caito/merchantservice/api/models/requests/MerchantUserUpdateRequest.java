package com.caito.merchantservice.api.models.requests;

import lombok.*;

import java.io.Serializable;

/*
 * MerchantUserUpdateRequest is a DTO representing the request model for updating a merchant user.
 *
 * @author Caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class MerchantUserUpdateRequest implements Serializable {
    private Long merchantId;
    private String fullName;
    private String phone;
    private String address;
    private String email;
}
