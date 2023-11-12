package com.logifuture.walletdemo.exceptions;

import java.math.BigDecimal;

import static java.lang.String.format;

public class NegativeFundsException extends RuntimeException {
  public NegativeFundsException(BigDecimal funds) {
    super(format("Cannot transaction on Funds: %s. Funds must be greater than 0", funds));
  }
}
