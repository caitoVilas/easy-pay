package com.caito.merchantservice.persistence.repositories;

import com.caito.merchantservice.persistence.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Merchant Repository for accessing Merchant entities from the database.
 *
 * @author Caito
 *
 */
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    boolean existsByEmail(String email);
    boolean existsByTaxId(Long taxId);
}
