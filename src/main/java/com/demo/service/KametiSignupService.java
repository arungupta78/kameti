package com.demo.service;

import com.demo.entity.PaymentDetails;
import com.demo.model.KametiSignupRequest;
import com.demo.model.KametiSuccessResponse;
import com.demo.repository.KametiDetailsRepository;
import com.demo.repository.PaymentDetailsRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KametiSignupService {
  @Autowired private KametiDetailsRepository kametiDetailsRepo;
  @Autowired private PaymentDetailsRepository paymentDetailsRepo;

  @Transactional
  public KametiSuccessResponse signUpKameti(KametiSignupRequest request) {
    var kametiDetails = kametiDetailsRepo.save(request.getKametiDetails());

    PaymentDetails paymentDetails = request.getPaymentDetails();
    if (BigDecimal.TEN.compareTo(paymentDetails.getAmount()) < 0) {
      throw new RuntimeException("Kameti can't be of more than 10 rupees");
    }

    paymentDetails.setKametiId(kametiDetails.getKametiId());
    var details = paymentDetailsRepo.save(paymentDetails);

    return KametiSuccessResponse.builder()
        .kametiId(kametiDetails.getKametiId())
        .paymentId(details.getPaymentId())
        .build();
  }
}
