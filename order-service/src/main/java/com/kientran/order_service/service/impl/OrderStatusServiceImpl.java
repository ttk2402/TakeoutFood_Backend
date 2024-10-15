package com.kientran.order_service.service.impl;

import com.kientran.order_service.dto.OrderStatusDto;
import com.kientran.order_service.entity.OrderStatus;
import com.kientran.order_service.exception.ResourceNotFoundException;
import com.kientran.order_service.repository.OrderStatusRepository;
import com.kientran.order_service.service.OrderStatusService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;
    private final ModelMapper modelMapper;

    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository, ModelMapper modelMapper) {
        this.orderStatusRepository = orderStatusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderStatusDto createOrderStatus(OrderStatusDto orderStatusDto) {
        OrderStatus orderStatus = this.modelMapper.map(orderStatusDto, OrderStatus.class);
        OrderStatus addOrderStatus = this.orderStatusRepository.save(orderStatus);
        return this.modelMapper.map(addOrderStatus, OrderStatusDto.class);
    }

    @Override
    public void deleteOrderStatus(Integer orderStatusId) {
        OrderStatus orderStatus = this.orderStatusRepository.findById(orderStatusId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderStatus ", "OrderStatusId", orderStatusId));
        this.orderStatusRepository.delete(orderStatus);
    }

    @Override
    public List<OrderStatusDto> getOrderStatuses() {
        List<OrderStatus> orderStatuses = this.orderStatusRepository.findAll();
        List<OrderStatusDto> orderStatusDtos = orderStatuses.stream().map((orderStatus)-> this.modelMapper.map(orderStatus, OrderStatusDto.class)).collect(Collectors.toList());
        return orderStatusDtos;
    }

    @Override
    public Optional<OrderStatus> findOrderStatus(String status) {
        OrderStatus orderStatus = this.orderStatusRepository.findOrderStatusByStatus(status);
        return Optional.ofNullable(orderStatus);
    }
}
