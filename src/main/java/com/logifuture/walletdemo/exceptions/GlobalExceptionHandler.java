package com.logifuture.walletdemo.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

  @ExceptionHandler(CreateWalletException.class)
  public ResponseEntity<String> handleCreateWalletException(CreateWalletException e) {
    log.error("Failed to create wallet. Error: {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(e.getMessage());
  }

  @ExceptionHandler(WalletNotFoundException.class)
  public ResponseEntity<String> handleWalletNotFoundException(WalletNotFoundException e) {
    log.error("Wallet Not Found. Error: {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(e.getMessage());
  }

  @ExceptionHandler(WalletAlreadyExistsException.class)
  public ResponseEntity<String> handleWalletAlreadyExistsException(WalletAlreadyExistsException e) {
    log.error("Wallet already exists. Error: {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(e.getMessage());
  }

  @ExceptionHandler(InsufficientBalanceException.class)
  public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException e) {
    log.error("Failed to create wallet. Error: {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(e.getMessage());
  }

  @ExceptionHandler(UnauthorisedUserException.class)
  public ResponseEntity<String> handleUnauthorisedUserException(UnauthorisedUserException e) {
    log.error("User is not authorised. Error: {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(e.getMessage());
  }

  @ExceptionHandler(NegativeFundsException.class)
  public ResponseEntity<String> handleNegativeFundsException(NegativeFundsException e) {
    log.error("Funds must be positive. Error: {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(e.getMessage());
  }

  @ExceptionHandler(DuplicateTransactionException.class)
  public ResponseEntity<String> handleDuplicateTransactionException(DuplicateTransactionException e) {
    log.error("Transactions must be unique. Error: {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(e.getMessage());
  }


  @ExceptionHandler(MissingPathVariableException.class)
  public ResponseEntity<String> handleMissingPathVariableException(MissingPathVariableException e) {
    log.error("Path variable cannot be null. Error: {}", e.getMessage());
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body("Path variable must not be null");
  }
}
