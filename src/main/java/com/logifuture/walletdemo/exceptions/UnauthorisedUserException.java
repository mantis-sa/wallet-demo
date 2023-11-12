package com.logifuture.walletdemo.exceptions;

import java.util.UUID;

import static java.lang.String.format;

public class UnauthorisedUserException extends RuntimeException {

  public UnauthorisedUserException(UUID userID) {
    super(format("Unauthorised - UserID: %s", userID.toString()));
  }
}
