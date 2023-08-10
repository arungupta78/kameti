package com.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "PaymentDetails")
@Table(name = "PAYMENT_DETAILS")
public class PaymentDetails {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  String paymentId;

  String cardNumber;
  String cardType;
  BigDecimal amount;
  Long kametiId;
}
