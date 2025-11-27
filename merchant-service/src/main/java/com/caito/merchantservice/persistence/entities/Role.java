package com.caito.merchantservice.persistence.entities;

import com.pp.commonsservice.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

/*
 * Role Entity representing a user role in the system.
 * Implements GrantedAuthority for Spring Security integration.
 * Mapped to the "roles" table in the database.
 *
 * @author Caito
 *
 */
@Entity
@Table(name = "roles")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleName role;

    @Override
    public @Nullable String getAuthority() {
        return role.name();
    }
}
