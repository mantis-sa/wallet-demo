package com.logifuture.walletdemo.exceptions;

import java.util.UUID;

import static java.lang.String.format;

public class WalletAlreadyExistsException extends RuntimeException {

  public WalletAlreadyExistsException(UUID userID) {
    super(format("Wallet already exists for UserID: %s", userID.toString()));
  }
}
