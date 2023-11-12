package com.logifuture.walletdemo.model.entity;

import com.logifuture.walletdemo.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder(toBuilder = true)
@Table
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {

  @Id
  @Column(nullable = false, updatable = false)
  private UUID id;
  @Column(nullable = false, updatable = false)
  private UUID walletId;
  @Column(nullable = false, updatable = false)
  @Enumerated(EnumType.STRING)
  private TransactionType type;
  @Column(nullable = false, updatable = false)
  private BigDecimal amount;
  @Column(nullable = false, updatable = false)
  private BigDecimal resultBalance;
  @Column(nullable = false, updatable = false)
  private ZonedDateTime createdAt;
}
