package com.logifuture.walletdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class WalletDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(WalletDemoApplication.class, args);
  }

}
