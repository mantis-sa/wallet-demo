package com.logifuture.walletdemo.service.factory;

import com.logifuture.walletdemo.model.Wallet;
import com.logifuture.walletdemo.service.TimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WalletFactory {

  private final TimeService timeService;

  public Wallet buildNewWallet(UUID user) {
    return Wallet.builder()
        .id(UUID.randomUUID())
        .userID(user)
        .balance(BigDecimal.ZERO)
        .createdAt(timeService.getCurrentZonedDateTime())
        .updatedAt(null)
        .build();
  }
}
