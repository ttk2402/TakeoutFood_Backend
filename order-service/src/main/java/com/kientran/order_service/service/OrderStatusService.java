package com.kientran.order_service.service;

import com.kientran.order_service.dto.OrderStatusDto;
import com.kientran.order_service.entity.OrderStatus;
import java.util.List;
import java.util.Optional;

public interface OrderStatusService {
    OrderStatusDto createOrderStatus(OrderStatusDto orderStatusDto);
    void deleteOrderStatus(Integer orderStatusId);
    List<OrderStatusDto> getOrderStatuses();
    Optional<OrderStatus> findOrderStatus(String status);
}