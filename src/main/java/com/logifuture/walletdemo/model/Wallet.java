package com.logifuture.walletdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder(toBuilder = true)
@Table(indexes = {
    @Index(columnList = "userID", unique = true)
})
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Wallet {

  @Id
  @GeneratedValue(generator = "uuid-hibernate-generator")
  @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(nullable = false, updatable = false)
  private UUID id;
  @Column(nullable = false, updatable = false, unique = true)
  private UUID userID;
  @Column(nullable = false)
  private BigDecimal balance;
  @Column(nullable = false, updatable = false)
  private ZonedDateTime createdAt;
  @Column(insertable = false)
  private ZonedDateTime updatedAt;

}
