package com.kientran.order_service.repository;

import com.kientran.order_service.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Integer> {}
