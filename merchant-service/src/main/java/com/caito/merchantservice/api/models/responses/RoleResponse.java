package com.caito.merchantservice.api.models.responses;

import com.pp.commonsservice.enums.RoleName;
import lombok.*;

import java.io.Serializable;

/*
 * RoleResponse is a DTO representing the response model for a role.
 *
 * @author Caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class RoleResponse implements Serializable {
    private Long id;
    private RoleName role;
}
