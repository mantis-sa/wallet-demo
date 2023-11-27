package com.logifuture.walletdemo.repository;

import com.logifuture.walletdemo.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

  Optional<Transaction> findTopByWalletIdOrderByCreatedAtDesc(UUID walletId);
}
