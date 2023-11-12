package com.logifuture.walletdemo.service;

import com.logifuture.walletdemo.exceptions.InsufficientBalanceException;
import com.logifuture.walletdemo.model.entity.Transaction;
import com.logifuture.walletdemo.model.request.rest.AddFundsRequest;
import com.logifuture.walletdemo.model.request.rest.RemoveFundsRequest;
import com.logifuture.walletdemo.service.factory.TransactionFactory;
import com.logifuture.walletdemo.service.integration.TransactionIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

  private final TransactionFactory transactionFactory;
  private final TransactionIntegrationService transactionIntegrationService;

  public void initialiseWallet(UUID walletId) {
    Transaction transaction = transactionFactory.buildWalletInitialisation(walletId);
    transactionIntegrationService.executeTransaction(transaction);
  }

  public void addFunds(UUID walletId, AddFundsRequest request) {
    Transaction latestTransaction = transactionIntegrationService.getLatestTransaction(walletId);
    Transaction addFundsTx = transactionFactory.buildAddFundsTransaction(walletId, request,
        latestTransaction.getResultBalance());
    Transaction resultTx = transactionFactory.aggregateTransactions(latestTransaction, addFundsTx);
    transactionIntegrationService.executeTransaction(resultTx);
  }

  public void removeFunds(UUID walletId, RemoveFundsRequest request) throws InsufficientBalanceException {
    Transaction latestTransaction = transactionIntegrationService.getLatestTransaction(walletId);
    Transaction removeFundsTx = transactionFactory.buildRemoveFundsTransaction(walletId, request,
        latestTransaction.getResultBalance());
    Transaction resultTx = transactionFactory.aggregateTransactions(latestTransaction, removeFundsTx);
    transactionIntegrationService.executeTransaction(resultTx);
  }
}
