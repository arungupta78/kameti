package com.demo.model;

import com.demo.entity.KametiDetails;
import com.demo.entity.PaymentDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KametiSignupRequest {
  private PaymentDetails paymentDetails;
  private KametiDetails kametiDetails;
}
