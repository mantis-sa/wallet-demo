package com.logifuture.walletdemo.exceptions;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

import static java.lang.String.format;

public class WalletNotFoundException extends RuntimeException {
  private WalletNotFoundException(String message) {
    super(message);
  }

  public static WalletNotFoundException fromWalletId(UUID walletID) {
    String message = format("Wallet not found - WalletID: %s", walletID.toString());
    return new WalletNotFoundException(message);
  }

  public static WalletNotFoundException fromUserId(UUID userId) {
    String message = format("Wallet not found for userId: %s", userId);
    return new WalletNotFoundException(message);
  }
}
