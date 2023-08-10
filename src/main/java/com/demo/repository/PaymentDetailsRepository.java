package com.demo.repository;

import com.demo.entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, String> {}
