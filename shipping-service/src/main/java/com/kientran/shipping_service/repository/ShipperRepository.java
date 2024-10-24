package com.kientran.shipping_service.repository;

import com.kientran.shipping_service.entity.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipperRepository extends JpaRepository<Shipper, Integer> {
    Optional<Shipper> findByAccountId(Integer accountId);
}
