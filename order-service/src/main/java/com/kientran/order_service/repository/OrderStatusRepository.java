package com.kientran.order_service.repository;

import com.kientran.order_service.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    @Query(value = "select * from order_status where status = :status", nativeQuery = true)
    OrderStatus findOrderStatusByStatus(@Param("status") String status);
}
