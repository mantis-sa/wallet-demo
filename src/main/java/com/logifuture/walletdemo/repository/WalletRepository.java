package com.logifuture.walletdemo.repository;

import com.logifuture.walletdemo.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {

  @Modifying
  @Query(value = "UPDATE Wallet AS w SET w.balance = w.balance + :funds, w.updatedAt = :updatedAt WHERE w.id = :walletID")
  Integer addFunds(@Param("walletID") UUID walletID, @Param("funds") BigDecimal funds,
                   @Param("updatedAt") ZonedDateTime updatedAt);

  @Modifying
  @Query(value = "UPDATE Wallet AS w SET w.balance = w.balance - :funds, w.updatedAt = :updatedAt WHERE w.id = :walletID AND w.balance - :funds >= 0")
  Integer removeFunds(@Param("walletID") UUID walletID, @Param("funds") BigDecimal funds,
                      @Param("updatedAt") ZonedDateTime updatedAt);

  Optional<Wallet> findWalletByUserID(UUID userId);
}