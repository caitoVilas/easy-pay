package com.caito.merchantservice.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/*
 * MerchantUser Entity representing a merchant user in the system.
 * Implements UserDetails for Spring Security integration.
 * Mapped to the "merchant_users" table in the database.
 *
 * @author Caito
 *
 */
@Entity
@Table(name = "merchant_users")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class MerchantUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String phone;
    private String address;
    private String email;
    private String password;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "merchant_user_roles",
            joinColumns = @JoinColumn(name = "merchant_user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
    @ManyToOne(fetch = FetchType.LAZY)
    private Merchant merchant;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) return null;
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getAuthority()))
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
