package com.logifuture.walletdemo.service;

import com.logifuture.walletdemo.exceptions.InsufficientBalanceException;
import com.logifuture.walletdemo.exceptions.WalletNotFoundException;
import com.logifuture.walletdemo.model.Wallet;
import com.logifuture.walletdemo.service.factory.WalletFactory;
import com.logifuture.walletdemo.service.integration.WalletIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

  private final WalletFactory walletFactory;
  private final ValidationService validationService;
  private final WalletIntegrationService walletIntegrationService;
  public UUID createWallet(UUID userID) {
    validationService.validateCreateWalletRequest(userID);
    Wallet wallet = walletFactory.buildNewWallet(userID);
    return walletIntegrationService.createWallet(wallet);
  }

  public Wallet getWalletSummary(UUID walletId) {
    return walletIntegrationService.getWalletSummary(walletId);
  }

  public void addFunds(UUID walletId, BigDecimal funds) throws WalletNotFoundException {
    walletIntegrationService.addFunds(walletId, funds);
  }

  public void removeFunds(UUID walletId, BigDecimal funds) throws WalletNotFoundException, InsufficientBalanceException {
    walletIntegrationService.removeFunds(walletId, funds);
  }
}
