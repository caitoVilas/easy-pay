package com.caito.merchantservice.services.contracts;

import com.caito.merchantservice.api.models.requests.MerchantRequest;
import com.caito.merchantservice.api.models.requests.MerchantUpdateRequest;
import com.caito.merchantservice.api.models.responses.MerchantResponse;
import com.caito.merchantservice.api.models.responses.MerchantUserResponse;

import java.util.List;

/*
 * MerchantService defines the contract for merchant-related operations.
 *
 * @author Caito
 *
 */
public interface MerchantService {

    MerchantResponse createMerchant(MerchantRequest merchantRequest);
    List<MerchantResponse> getAllMerchants();
    List<MerchantUserResponse> getAllUsersbyMerchantId(Long merchantId);
    MerchantResponse getMerchantById(Long merchantId);
    MerchantResponse updateMerchant(Long merchantId, MerchantUpdateRequest request);
    void deleteMerchant(Long merchantId);
}
