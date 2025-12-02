package com.caito.merchantservice.api.models.responses;

import com.caito.merchantservice.utils.enums.BusinessYtpe;
import com.caito.merchantservice.utils.enums.MerchantStatus;
import lombok.*;

import java.io.Serializable;

/*
 * MerchantResponse is a DTO representing the response model for merchant data.
 *
 * @author Caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class MerchantResponse implements Serializable {
    private Long id;
    private String businessName;
    private String taxId;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private MerchantStatus status;
    private BusinessYtpe businessType;
    private String webhookUrl;
}
