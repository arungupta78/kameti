package com.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity(name = "KametiDetails")
@Table(name = "KAMETI_DETAILS")
public class KametiDetails {
  @Id @GeneratedValue Long kametiId;

  int totalMembers;
  BigDecimal poolSize;
}
