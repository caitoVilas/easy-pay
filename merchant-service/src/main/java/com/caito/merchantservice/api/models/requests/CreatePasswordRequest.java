package com.caito.merchantservice.api.models.requests;

import lombok.*;

import java.io.Serializable;

/*
 * CreatePasswordRequest represents the request payload for creating a password for a user.
 *
 * @author Caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class CreatePasswordRequest implements Serializable {
    private Long merchantId;
    private Long userId;
    private String password;
    private String confirmPassword;
}
