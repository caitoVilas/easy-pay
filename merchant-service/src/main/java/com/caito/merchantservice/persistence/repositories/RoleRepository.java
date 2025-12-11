package com.caito.merchantservice.persistence.repositories;

import com.caito.merchantservice.persistence.entities.Role;
import com.pp.commonsservice.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * Role Repository for accessing Role entities from the database.
 *
 * @author Caito
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleName role);
}
