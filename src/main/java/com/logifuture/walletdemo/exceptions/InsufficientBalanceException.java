package com.logifuture.walletdemo.exceptions;

import java.util.UUID;

import static java.lang.String.format;

public class InsufficientBalanceException extends RuntimeException {
  public InsufficientBalanceException(UUID walletId) {
    super(format("Post-transaction balance must not be negative. WalletId: %s", walletId.toString()));
  }
}
