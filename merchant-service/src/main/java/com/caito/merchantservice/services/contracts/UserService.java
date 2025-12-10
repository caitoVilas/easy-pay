package com.caito.merchantservice.services.contracts;

import com.caito.merchantservice.api.models.requests.MerchantUserRequest;
import com.caito.merchantservice.api.models.responses.MerchantUserResponse;

/*
 * UserService defines the contract for user-related operations.
 *
 * @author Caito
 *
 */
public interface UserService {
    MerchantUserResponse createUser(Long merchantId, MerchantUserRequest request);
}
