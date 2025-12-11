package com.caito.merchantservice.api.models.responses;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/*
 * MerchantUserResponse is a DTO representing the response model for a merchant user.
 *
 * @author Caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class MerchantUserResponse implements Serializable {
    private long id;
    private String fullName;
    private String phone;
    private String address;
    private String email;
    private List<RoleResponse> roles;
}
