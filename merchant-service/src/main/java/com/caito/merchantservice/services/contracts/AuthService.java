package com.caito.merchantservice.services.contracts;

import com.caito.merchantservice.api.models.requests.AuthRequest;
import com.caito.merchantservice.api.models.responses.AuthResponse;

/**
 * Service contract for authentication operations.
 * Defines methods for user login.
 *
 * @author caito
 *
 */
public interface AuthService {
    AuthResponse login(AuthRequest request);
}
