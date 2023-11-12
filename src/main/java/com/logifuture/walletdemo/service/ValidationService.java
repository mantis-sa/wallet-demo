package com.logifuture.walletdemo.service;

import com.logifuture.walletdemo.exceptions.DuplicateTransactionException;
import com.logifuture.walletdemo.exceptions.NegativeFundsException;
import com.logifuture.walletdemo.exceptions.WalletAlreadyExistsException;
import com.logifuture.walletdemo.model.request.rest.AddFundsRequest;
import com.logifuture.walletdemo.model.request.rest.RemoveFundsRequest;
import com.logifuture.walletdemo.service.integration.TransactionIntegrationService;
import com.logifuture.walletdemo.service.integration.WalletIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ValidationService {

  private final WalletIntegrationService walletIntegrationService;
  private final TransactionIntegrationService transactionIntegrationService;

  private static final int NEGATIVE_FUNDS_FLAG = -1;

  public void validateCreateWalletRequest(UUID userID) throws WalletAlreadyExistsException {
    var optionalWallet = walletIntegrationService.getWallet(userID);
    if (optionalWallet.isPresent()) {
      throw new WalletAlreadyExistsException(userID);
    }
  }

  public void validateAddFundsRequest(AddFundsRequest request) throws NegativeFundsException {
    validateFundsAmount(request.getFunds());
    validateUniqueTransaction(request.getTransactionId());
  }

  public void validateRemoveFundsRequest(RemoveFundsRequest request) throws NegativeFundsException {
    validateFundsAmount(request.getFunds());
    validateUniqueTransaction(request.getTransactionId());
  }

  private void validateFundsAmount(BigDecimal funds) throws NegativeFundsException {
    if (funds.compareTo(BigDecimal.ZERO) == NEGATIVE_FUNDS_FLAG) {
      throw new NegativeFundsException(funds);
    }
  }

  private void validateUniqueTransaction(UUID transactionId) throws DuplicateTransactionException {
    var transaction = this.transactionIntegrationService.getTransaction(transactionId);
    if (transaction.isPresent()) {
      throw new DuplicateTransactionException(transactionId);
    }
  }
}
