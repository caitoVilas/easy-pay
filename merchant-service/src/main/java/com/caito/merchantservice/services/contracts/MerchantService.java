package com.caito.merchantservice.services.contracts;

import com.caito.merchantservice.api.models.requests.MerchantRequest;
import com.caito.merchantservice.api.models.responses.MerchantResponse;

/*
 * MerchantService defines the contract for merchant-related operations.
 *
 * @author Caito
 *
 */
public interface MerchantService {

    MerchantResponse createMerchant(MerchantRequest merchantRequest);
}
