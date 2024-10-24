package com.kientran.shipping_service.repository;

import com.kientran.shipping_service.entity.DeliveryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Integer> {
}