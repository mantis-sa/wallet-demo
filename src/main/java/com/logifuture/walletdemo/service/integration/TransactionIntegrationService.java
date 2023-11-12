package com.logifuture.walletdemo.service.integration;

import com.logifuture.walletdemo.exceptions.InsufficientBalanceException;
import com.logifuture.walletdemo.exceptions.WalletNotFoundException;
import com.logifuture.walletdemo.model.Wallet;
import com.logifuture.walletdemo.model.entity.Transaction;
import com.logifuture.walletdemo.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@Validated
@RequiredArgsConstructor
public class TransactionIntegrationService {

  private final TransactionRepository transactionRepository;
  public void executeTransaction(Transaction transaction) {
    log.debug("ExecuteTransaction - Transaction: {}", transaction);
    transactionRepository.save(transaction);
    log.debug("ExecuteTransaction - TransactionId: {}, Status: Success", transaction.getId());
  }

  public Transaction getLatestTransaction(UUID walletId) {
    log.debug("GetLatestTransaction - WalletId: {}", walletId);
    Optional<Transaction> latestTransaction = transactionRepository.findTopByWalletIdOrderByCreatedAtDesc(walletId);
    log.debug("GetLatestTransaction - WalletId: {}, Status: Success", walletId);
    return latestTransaction.orElseThrow(() -> new RuntimeException(""));
  }

  public Optional<Transaction> getTransaction(UUID transactionId) {
    log.debug("GetTransaction - TransactionId: {}", transactionId);
    Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
    log.debug("GetTransaction - TransactionId: {}, Status: Success", transactionId);
    return optionalTransaction;
  }
}
