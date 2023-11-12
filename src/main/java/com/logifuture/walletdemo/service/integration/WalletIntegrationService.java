package com.logifuture.walletdemo.service.integration;

import com.logifuture.walletdemo.exceptions.CreateWalletException;
import com.logifuture.walletdemo.exceptions.InsufficientBalanceException;
import com.logifuture.walletdemo.exceptions.WalletNotFoundException;
import com.logifuture.walletdemo.model.Wallet;
import com.logifuture.walletdemo.repository.WalletRepository;
import com.logifuture.walletdemo.service.TimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
@Validated
@RequiredArgsConstructor
public class WalletIntegrationService {

  private static final int NO_ROWS_UPDATED = 0;

  private final WalletRepository walletRepository;
  private final TimeService timeService;

  public UUID createWallet(Wallet wallet) throws CreateWalletException {
    UUID userID = wallet.getUserID();
    log.debug("Create Wallet in DB - User: {}", userID);
    try {
      Wallet persistedWallet = walletRepository.save(wallet);
      log.debug("Created Wallet in DB for User: {}, Wallet ID: {}", userID, persistedWallet.getId());
      return persistedWallet.getId();
    } catch (Exception e) {
      var createWalletException = new CreateWalletException(wallet.getUserID(), e);
      log.error("CreateWalletException - Message: {}", createWalletException.getMessage());
      throw createWalletException;
    }
  }

  public Wallet getWalletSummary(UUID walletId) throws WalletNotFoundException {
    log.debug("GetWalletSummary - WalletID: {}", walletId);
    Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> WalletNotFoundException.fromWalletId(walletId));
    log.debug("GetWalletSummary - Wallet: {}", wallet);
    return wallet;
  }

  public void addFunds(UUID walletId, BigDecimal funds) throws WalletNotFoundException {
    log.debug("AddFunds - WalletId: {}, Funds: {}", walletId, funds);
    Integer noOfRowsUpdated = walletRepository.addFunds(
        walletId, funds, timeService.getCurrentZonedDateTime());
    if (noOfRowsUpdated == NO_ROWS_UPDATED) {
      throw WalletNotFoundException.fromWalletId(walletId);
    }
    log.debug("AddFunds - WalletId: {}, Status: Success", walletId);
  }

  public void removeFunds(UUID walletId, BigDecimal funds) throws WalletNotFoundException, InsufficientBalanceException {
    log.debug("RemoveFunds - WalletId: {}, Funds: {}", walletId, funds);
    Wallet wallet = walletRepository.findById(walletId).orElseThrow(() ->
        WalletNotFoundException.fromWalletId(walletId));
    Integer noOfRowsUpdated = walletRepository.removeFunds(wallet.getId(), funds,
        timeService.getCurrentZonedDateTime());
    if (noOfRowsUpdated == NO_ROWS_UPDATED) {
      throw new InsufficientBalanceException(wallet.getId());
    }
    log.debug("RemoveFunds - WalletId: {}, Status: Success", wallet.getId());
  }

  public Wallet findWallet(UUID walletId) throws WalletNotFoundException {
    log.debug("FindWallet - WalletId: {}", walletId);
    Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> WalletNotFoundException.fromWalletId(walletId));
    log.debug("FindWallet - WalletId: {}, Status: Success", walletId);
    return wallet;
  }

  public Optional<Wallet> getWallet(UUID userID) throws WalletNotFoundException {
    log.debug("FindWalletByUserId - UserId: {}", userID);
    var optionalWallet = walletRepository.findWalletByUserID(userID);
    log.debug("FindWalletByUserId - UserId: {}, Status: Success", userID);
    return optionalWallet;
  }
}
