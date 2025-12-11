package com.caito.merchantservice.services.contracts;

import com.caito.merchantservice.api.models.requests.CreatePasswordRequest;
import com.caito.merchantservice.api.models.requests.MerchantUserRequest;
import com.caito.merchantservice.api.models.requests.MerchantUserUpdateRequest;
import com.caito.merchantservice.api.models.responses.MerchantUserResponse;

/*
 * UserService defines the contract for user-related operations.
 *
 * @author Caito
 *
 */
public interface UserService {
    MerchantUserResponse createUser(Long merchantId, MerchantUserRequest request);
    MerchantUserResponse getUserById(Long userId, Long merchantId);
    MerchantUserResponse updateUser(Long userId, MerchantUserUpdateRequest request);
    void deleteUser(Long userId, Long merchantId);
    void createPassword(CreatePasswordRequest request);
}
