package com.kientran.product_service.repository;

import com.kientran.product_service.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {}
