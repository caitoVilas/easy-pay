package com.caito.merchantservice.api.models.requests;

import com.caito.merchantservice.utils.enums.BusinessYtpe;
import lombok.*;

import java.io.Serializable;

/*
 * MerchantUpdateRequest represents the data required to update a merchant's information.
 *
 * @author Caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class MerchantUpdateRequest implements Serializable {
    private String businessName;
    private String TaxId;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private BusinessYtpe businessType;
    private String webhookUrl;
}
