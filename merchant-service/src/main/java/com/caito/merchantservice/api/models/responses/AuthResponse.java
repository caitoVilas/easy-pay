package com.caito.merchantservice.api.models.responses;

import lombok.*;

import java.io.Serializable;

/**
 * Represents an authentication response containing the access token.
 * This class is used to encapsulate the JWT token
 * provided to a user upon successful authentication.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class AuthResponse implements Serializable {
    private String accessToken;
}
