package com.caito.merchantservice.api.models.requests;

import lombok.*;

import java.io.Serializable;

/**
 * Represents an authentication request containing user credentials.
 * This class is used to encapsulate the email and password
 * provided by a user during the authentication process.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class AuthRequest implements Serializable {
    private String email;
    private String password;
}
