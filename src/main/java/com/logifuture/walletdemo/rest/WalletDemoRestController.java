package com.logifuture.walletdemo.rest;

import com.logifuture.walletdemo.model.request.rest.AddFundsRequest;
import com.logifuture.walletdemo.model.request.rest.RemoveFundsRequest;
import com.logifuture.walletdemo.service.WalletDemoService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Log4j2
public class WalletDemoRestController {

  private final WalletDemoService walletDemoService;

  @PostMapping(value = "/wallets/user/{id}")
  @ResponseBody
  public String createWallet(@PathVariable("id") @NotNull final UUID userId) {
    log.debug("CreateWallet - Start - UserID: {}", userId.toString());
    String walletId = walletDemoService.createWallet(userId);
    log.debug("CreateWallet - End - Wallet: {}", userId.toString());
    return walletId;
  }

  @PutMapping(value = "/wallets/{id}/funds/add")
  @ResponseBody
  public void addFunds(@PathVariable("id") @NotNull final UUID walletId,
                       @RequestHeader("userID") UUID userID,
                       @RequestBody @NotNull AddFundsRequest request) {
    log.debug("AddFunds - Start - UserID: {}, WalletID: {}, Funds: {}",
        userID, walletId.toString(), request.getFunds());
    walletDemoService.addFunds(walletId, userID, request);
    log.debug("AddFunds - End - UserID: {}, Wallet: {}", userID, walletId.toString());
  }

  @PutMapping(value = "/wallets/{id}/funds/remove")
  @ResponseBody
  public void removeFunds(@PathVariable("id") @NotNull final UUID walletId,
                          @RequestHeader("userID") UUID userID,
                          @RequestBody @NotNull RemoveFundsRequest request) {
    log.debug("RemoveFunds - Start - UserID: {}, WalletID: {}, Funds: {}",
        userID, walletId.toString(), request.getFunds());
    walletDemoService.removeFunds(walletId, userID, request);
    log.debug("RemoveFunds - End - UserID: {}, Wallet: {}", userID, walletId.toString());
  }

  @GetMapping(value = "/wallets/{id}/summary")
  @ResponseBody
  public String walletSummary(@PathVariable("id") @NotNull final UUID walletId,
                              @RequestHeader("userID") UUID userID) {
    log.debug("WalletSummary - Start - UserID: {}, WalletID: {}", userID, walletId.toString());
    String walletSummary = walletDemoService.getWalletSummary(walletId, userID);
    log.debug("WalletSummary - End - UserID: {}, WalletID: {}", userID, walletId.toString());
    return walletSummary;
  }
}
