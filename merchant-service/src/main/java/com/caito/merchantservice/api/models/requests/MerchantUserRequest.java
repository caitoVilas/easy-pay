package com.caito.merchantservice.api.models.requests;

import com.pp.commonsservice.enums.RoleName;
import lombok.*;

import java.io.Serializable;

/*
 * MerchantUserRequest is a DTO representing the request model for creating or updating a merchant user.
 *
 * @author Caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class MerchantUserRequest implements Serializable {
    private String fullName;
    private String phone;
    private String address;
    private String email;
    private String password;
    private RoleName role;
}
