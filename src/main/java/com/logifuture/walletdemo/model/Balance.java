package com.logifuture.walletdemo.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Balance {
  private BigDecimal balance;
}
