package com.caito.merchantservice.api.models.requests;

import com.caito.merchantservice.utils.enums.MerchantStatus;
import lombok.*;

import java.io.Serializable;

/*
 * CreateMerchantRequest is a DTO representing the request model for creating a new merchant.
 *
 * @author Caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class CreateMerchantRequest implements Serializable {
    private String businessName;
    private String TaxId;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private MerchantStatus status;
    private MerchantStatus businessType;
    private String webhookUrl;
    private MerchantUserRequest user;
}
