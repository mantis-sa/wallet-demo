package com.logifuture.walletdemo.exceptions;

import java.util.UUID;

import static java.lang.String.*;

public class CreateWalletException extends RuntimeException {
  public CreateWalletException(UUID userID, Throwable cause) {
    super(format("Failed to create wallet for UserID: %s", userID.toString()), cause);
  }
}
