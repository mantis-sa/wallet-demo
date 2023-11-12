package com.logifuture.walletdemo.service;

import com.logifuture.walletdemo.exceptions.CreateWalletException;
import com.logifuture.walletdemo.exceptions.InsufficientBalanceException;
import com.logifuture.walletdemo.exceptions.NegativeFundsException;
import com.logifuture.walletdemo.exceptions.UnauthorisedUserException;
import com.logifuture.walletdemo.exceptions.WalletAlreadyExistsException;
import com.logifuture.walletdemo.exceptions.WalletNotFoundException;
import com.logifuture.walletdemo.model.Wallet;
import com.logifuture.walletdemo.model.request.rest.AddFundsRequest;
import com.logifuture.walletdemo.model.request.rest.RemoveFundsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletDemoService {

  private final AuthorisationService authorisationService;
  private final ValidationService validationService;
  private final WalletService walletService;
  private final TransactionService transactionService;

  @Transactional
  public String createWallet(UUID userID) throws CreateWalletException, WalletAlreadyExistsException {
    UUID walletId = walletService.createWallet(userID);
    transactionService.initialiseWallet(walletId);
    return walletId.toString();
  }

  public String getWalletSummary(UUID walletId, UUID userID) throws UnauthorisedUserException {
    authorisationService.authoriseRequest(userID, walletId);
    Wallet walletSummary = walletService.getWalletSummary(walletId);
    return walletSummary.toString();
  }

  @Transactional
  public void addFunds(UUID walletId, UUID userId, AddFundsRequest request) throws UnauthorisedUserException,
      WalletNotFoundException, NegativeFundsException {
    authorisationService.authoriseRequest(userId, walletId);
    validationService.validateAddFundsRequest(request);
    walletService.addFunds(walletId, request.getFunds());
    transactionService.addFunds(walletId, request);
  }

  @Transactional
  public void removeFunds(UUID walletId, UUID userId, RemoveFundsRequest request) throws UnauthorisedUserException,
      WalletNotFoundException, NegativeFundsException, InsufficientBalanceException {
    authorisationService.authoriseRequest(userId, walletId);
    validationService.validateRemoveFundsRequest(request);
    walletService.removeFunds(walletId, request.getFunds());
    transactionService.removeFunds(walletId, request);
  }
}
