package com.logifuture.walletdemo.model.request.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class RemoveFundsRequest {
  private UUID transactionId;
  private BigDecimal funds;
}
