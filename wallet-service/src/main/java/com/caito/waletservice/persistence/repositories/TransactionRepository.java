package com.caito.waletservice.persistence.repositories;

import com.caito.waletservice.persistence.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Transaction entities.
 *
 * @author caito
 *
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
