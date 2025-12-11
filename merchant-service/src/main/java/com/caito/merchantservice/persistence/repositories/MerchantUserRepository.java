package com.caito.merchantservice.persistence.repositories;

import com.caito.merchantservice.persistence.entities.Merchant;
import com.caito.merchantservice.persistence.entities.MerchantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
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
    List<MerchantUser> findByMerchant(Merchant merchant);
    @Query("SELECT u FROM MerchantUser u WHERE u.email = ?2 AND u.id <> ?1")
    MerchantUser findByEmailAndNotId(Long id, String email);
}
