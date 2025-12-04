package com.caito.merchantservice.utils.mappers;

import com.caito.merchantservice.api.models.responses.RoleResponse;
import com.caito.merchantservice.persistence.entities.Role;

/*
 * RoleMapper is responsible for converting between Role entity and RoleResponse DTO.
 *
 * @author Caito
 *
 */
public class RoleMapper {

    public static RoleResponse mapToDto(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .role(role.getRole())
                .build();
    }
}
