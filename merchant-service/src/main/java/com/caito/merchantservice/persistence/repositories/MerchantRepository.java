package com.caito.merchantservice.persistence.repositories;

import com.caito.merchantservice.persistence.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
 * Merchant Repository for accessing Merchant entities from the database.
 *
 * @author Caito
 *
 */
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    boolean existsByEmail(String email);
    boolean existsByTaxId(String taxId);
    @Query("SELECT m FROM Merchant m WHERE m.TaxId = ?2 AND m.id <> ?1")
    Merchant findByTaxIdAndNotId(Long id, String taxId);
    @Query("SELECT m FROM Merchant m WHERE m.email = ?2 AND m.id <> ?1")
    Merchant findByEmailAndNotId(Long id, String email);

    String id(Long id);
}
