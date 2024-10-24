package com.kientran.shipping_service.repository;

import com.kientran.shipping_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
