package com.caito.merchantservice.api.models.requests;

import com.caito.merchantservice.utils.enums.BusinessYtpe;
import com.caito.merchantservice.utils.enums.MerchantStatus;
import lombok.*;

import java.io.Serializable;

/*
 * MerchantRequest is a DTO representing the request model for merchant data.
 *
 * @author Caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class MerchantRequest implements Serializable {
    private String businessName;
    private String taxId;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private BusinessYtpe businessType;
    private String webhookUrl;
    private MerchantUserRequest user;
}
