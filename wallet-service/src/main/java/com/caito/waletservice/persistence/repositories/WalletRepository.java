package com.caito.waletservice.persistence.repositories;

import com.caito.waletservice.persistence.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Wallet entities.
 *
 * @author caito
 *
 */
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(Long userId);
}
