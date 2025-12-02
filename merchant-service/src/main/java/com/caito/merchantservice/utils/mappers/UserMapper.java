package com.caito.merchantservice.utils.mappers;

import com.caito.merchantservice.api.models.requests.MerchantUserRequest;
import com.caito.merchantservice.api.models.responses.MerchantUserResponse;
import com.caito.merchantservice.persistence.entities.MerchantUser;

/*
 * UserMapper is responsible for converting between MerchantUserRequest/Response DTOs and MerchantUser entity.
 *
 * @author Caito
 *
 */
public class UserMapper {

    /*     * Map MerchantUserRequest to MerchantUser entity
     *
     * @param request MerchantUserRequest
     * @return MerchantUser entity
     */
    public static MerchantUser mapToEntity(MerchantUserRequest request) {
        return MerchantUser.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
    }

    /*     * Map MerchantUser entity to MerchantUserResponse DTO
     *
     * @param merchantUser MerchantUser entity
     * @return MerchantUserResponse DTO
     */
    public static MerchantUserResponse mapToDto(MerchantUser merchantUser) {
        return MerchantUserResponse.builder()
                .id(merchantUser.getId())
                .fullName(merchantUser.getFullName())
                .email(merchantUser.getEmail())
                .phone(merchantUser.getPhone())
                .address(merchantUser.getAddress())
                .build();
    }
}
