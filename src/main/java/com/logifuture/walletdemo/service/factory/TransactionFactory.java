package com.logifuture.walletdemo.service.factory;

import com.logifuture.walletdemo.exceptions.InsufficientBalanceException;
import com.logifuture.walletdemo.model.TransactionType;
import com.logifuture.walletdemo.model.Wallet;
import com.logifuture.walletdemo.model.entity.Transaction;
import com.logifuture.walletdemo.model.request.rest.AddFundsRequest;
import com.logifuture.walletdemo.model.request.rest.RemoveFundsRequest;
import com.logifuture.walletdemo.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class TransactionFactory {

  private static final int NEGATIVE_RESULT_BALANCE_FLAG = -1;

  private final TimeService timeService;

  public Transaction buildWalletInitialisation(UUID walletId) {
    return buildTransaction(UUID.randomUUID(), walletId, BigDecimal.ZERO, TransactionType.ADD, BigDecimal.ZERO);
  }

  public Transaction buildAddFundsTransaction(UUID walletId, AddFundsRequest request, BigDecimal resultBalance) {
    return buildTransaction(request.getTransactionId(), walletId, request.getFunds(), TransactionType.ADD,
        request.getFunds().add(resultBalance));
  }

  public Transaction buildRemoveFundsTransaction(UUID walletId, RemoveFundsRequest request, BigDecimal resultBalance) {
    return buildTransaction(request.getTransactionId(), walletId, request.getFunds(), TransactionType.REMOVE,
        request.getFunds().subtract(resultBalance));
  }

  public Transaction buildTransaction(UUID txId, UUID walletId, BigDecimal amount,
                                      TransactionType transactionType, BigDecimal resultBalance) {
    return Transaction.builder()
        .id(txId)
        .type(transactionType)
        .walletId(walletId)
        .amount(amount)
        .resultBalance(resultBalance)
        .createdAt(timeService.getCurrentZonedDateTime())
        .build();
  }

  private BiFunction<BigDecimal, BigDecimal, BigDecimal> addTransactions(BigDecimal tx1, BigDecimal tx2) {
    return (transaction, transaction2) -> tx1.add(tx2);
  }

  private BiFunction<BigDecimal, BigDecimal, BigDecimal> subtractTransactions(BigDecimal tx1, BigDecimal tx2) {
    return (transaction, transaction2) -> tx1.subtract(tx2);
  }

  public Transaction aggregateTransactions(Transaction latestTransaction, Transaction currentTx) {
    var aggFunction = currentTx.getType().equals(TransactionType.ADD) ?
        addTransactions(latestTransaction.getResultBalance(), currentTx.getAmount()) :
        subtractTransactions(latestTransaction.getResultBalance(), currentTx.getAmount());

    BigDecimal result = aggFunction.apply(latestTransaction.getResultBalance(), currentTx.getAmount());
    if (result.signum() == NEGATIVE_RESULT_BALANCE_FLAG) {
      throw new InsufficientBalanceException(currentTx.getWalletId());
    }
    return buildTransaction(currentTx.getId(), currentTx.getWalletId(), currentTx.getAmount(),
        TransactionType.ADD, result);
  }
}
