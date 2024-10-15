package com.kientran.order_service.repository;

import com.kientran.order_service.entity.DeliveryInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryInformationRepository extends JpaRepository<DeliveryInformation, Integer> {}
