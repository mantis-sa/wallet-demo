package com.logifuture.walletdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TimeService {

  private final Clock clock;

  public ZonedDateTime getCurrentZonedDateTime() {
    return ZonedDateTime.now(clock);
  }
}
