package com.logifuture.walletdemo.exceptions;

import java.util.UUID;

import static java.lang.String.format;

public class DuplicateTransactionException extends RuntimeException {

  public DuplicateTransactionException(UUID transactionId) {
    super(format("Duplicate transaction exists - TransactionId: %s", transactionId.toString()));
  }
}
