package com.caito.merchantservice.utils.mappers;

import com.caito.merchantservice.api.models.requests.MerchantRequest;
import com.caito.merchantservice.api.models.responses.MerchantResponse;
import com.caito.merchantservice.persistence.entities.Merchant;

/*
 * MerchantMapper is a utility class for mapping between Merchant entities and DTOs.
 *
 * @author Caito
 *
 */
public class MerchantMapper {

    /*     * Map MerchantRequest DTO to Merchant entity
     *
     * @param request MerchantRequest
     * @return Merchant entity
     */
    public static Merchant mapTOEntity(MerchantRequest request) {
        return Merchant.builder()
                .businessName(request.getBusinessName())
                .TaxId(request.getTaxId())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .city(request.getCity())
                .state(request.getState())
                .zipCode(request.getZipCode())
                .country(request.getCountry())
                .businessType(request.getBusinessType())
                .webhookUrl(request.getWebhookUrl())
                .build();
    }

    /*     * Map Merchant entity to MerchantResponse DTO
     *
     * @param merchant Merchant entity
     * @return MerchantResponse DTO
     */
    public static MerchantResponse mapToDto(Merchant merchant) {
        return MerchantResponse.builder()
                .id(merchant.getId())
                .businessName(merchant.getBusinessName())
                .taxId(merchant.getTaxId())
                .email(merchant.getEmail())
                .phone(merchant.getPhone())
                .address(merchant.getAddress())
                .city(merchant.getCity())
                .state(merchant.getState())
                .zipCode(merchant.getZipCode())
                .country(merchant.getCountry())
                .status(merchant.getStatus())
                .businessType(merchant.getBusinessType())
                .webhookUrl(merchant.getWebhookUrl())
                .build();
    }
}
