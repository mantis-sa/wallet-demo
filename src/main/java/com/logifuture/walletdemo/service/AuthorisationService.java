package com.logifuture.walletdemo.service;

import com.logifuture.walletdemo.exceptions.UnauthorisedUserException;
import com.logifuture.walletdemo.exceptions.WalletNotFoundException;
import com.logifuture.walletdemo.model.Wallet;
import com.logifuture.walletdemo.service.integration.WalletIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthorisationService {

  private final WalletIntegrationService walletIntegrationService;

  public void authoriseRequest(UUID userID, UUID walletID) throws WalletNotFoundException, UnauthorisedUserException {
    Wallet wallet = walletIntegrationService.findWallet(walletID);
    if (!wallet.getUserID().equals(userID)) {
      throw new UnauthorisedUserException(userID);
    }
  }
}
