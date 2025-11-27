package com.caito.merchantservice.persistence.repositories;

import com.caito.merchantservice.persistence.entities.MerchantUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * MerchantUser Repository for accessing MerchantUser entities from the database.
 *
 * @author Caito
 *
 */
public interface MerchantUserRepository extends JpaRepository<MerchantUser, Long> {
    Optional<MerchantUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
