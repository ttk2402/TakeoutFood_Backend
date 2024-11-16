package com.kientran.shipping_service.service.impl;

import com.kientran.shipping_service.dto.DeliveryOrderDto;
import com.kientran.shipping_service.dto.ResDeliveryOrderDto;
import com.kientran.shipping_service.entity.DeliveryOrder;
import com.kientran.shipping_service.entity.Shipper;
import com.kientran.shipping_service.exception.ResourceNotFoundException;
import com.kientran.shipping_service.repository.DeliveryOrderRepository;
import com.kientran.shipping_service.repository.ShipperRepository;
import com.kientran.shipping_service.service.DeliveryOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryOrderServiceImpl implements DeliveryOrderService {

    private final DeliveryOrderRepository deliveryOrderRepository;
    private final ShipperRepository shipperRepository;
    private final ModelMapper modelMapper;

    public DeliveryOrderServiceImpl(DeliveryOrderRepository deliveryOrderRepository, ShipperRepository shipperRepository, ModelMapper modelMapper) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.shipperRepository = shipperRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResDeliveryOrderDto createDeliveryOrder(DeliveryOrderDto deliveryOrderDto, Integer shipperId) {
        DeliveryOrder deliveryOrder = this.modelMapper.map(deliveryOrderDto, DeliveryOrder.class);
        Shipper shipper = this.shipperRepository.findById(shipperId).orElseThrow(() -> new ResourceNotFoundException("Shipper", "ShipperId", shipperId));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatter);
        deliveryOrder.setReceiveDate(formattedDate);
        deliveryOrder.setIsCompleted(false);
        deliveryOrder.setShipper(shipper);
        DeliveryOrder addDeliveryOrder = this.deliveryOrderRepository.save(deliveryOrder);
        return this.modelMapper.map(addDeliveryOrder, ResDeliveryOrderDto.class);
    }

    @Override
    public ResDeliveryOrderDto getDeliveryOrder(Integer deliveryOrderId) {
        DeliveryOrder deliveryOrder = this.deliveryOrderRepository.findById(deliveryOrderId).orElseThrow(() -> new ResourceNotFoundException("DeliveryOrder", "DeliveryId", deliveryOrderId));
        return this.modelMapper.map(deliveryOrder, ResDeliveryOrderDto.class);
    }

    @Override
    public Integer deleteDeliveryOrder(Integer deliveryOrderId) {
        DeliveryOrder deliveryOrder = this.deliveryOrderRepository.findById(deliveryOrderId).orElseThrow(() -> new ResourceNotFoundException("DeliveryOrder", "DeliveryId", deliveryOrderId));
        Integer orderId = deliveryOrder.getOrderId();
        this.deliveryOrderRepository.delete(deliveryOrder);
        return orderId;
    }

    @Override
    public List<ResDeliveryOrderDto> getAllDeliveryOrder() {
        List<DeliveryOrder> deliveryOrders = this.deliveryOrderRepository.findAll();
        return deliveryOrders.stream().map((deliveryOrder) -> this.modelMapper.map(deliveryOrder, ResDeliveryOrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResDeliveryOrderDto> getAllDeliveryOrderCurrentDto() {
        List<DeliveryOrder> deliveryOrders = this.deliveryOrderRepository.findAll();
        return deliveryOrders.stream()
                .filter(deliveryOrder -> deliveryOrder.getImageConfirmation() == null ) // Lọc đơn hàng khi chưa có ảnh xác nhận
                .map(deliveryOrder -> this.modelMapper.map(deliveryOrder, ResDeliveryOrderDto.class)) // Ánh xạ đối tượng
                .collect(Collectors.toList());
    }

    @Override
    public List<ResDeliveryOrderDto> getAllDeliveryOrderCompleteDto() {
        List<DeliveryOrder> deliveryOrders = this.deliveryOrderRepository.findAll();
        return deliveryOrders.stream()
                .filter(DeliveryOrder::getIsCompleted) // Lọc đơn hàng có isCompleted = true
                .map(deliveryOrder -> this.modelMapper.map(deliveryOrder, ResDeliveryOrderDto.class)) // Ánh xạ đối tượng
                .collect(Collectors.toList());
    }

    @Override
    public ResDeliveryOrderDto addImageConfirmation(Integer deliveryOrderId, String imageUrl) {
        DeliveryOrder deliveryOrder = this.deliveryOrderRepository.findById(deliveryOrderId).orElseThrow(() -> new ResourceNotFoundException("DeliveryOrder", "DeliveryId", deliveryOrderId));
        deliveryOrder.setImageConfirmation(imageUrl);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(formatter);
        deliveryOrder.setCompleteDate(formattedDate);
        DeliveryOrder updateDeliveryOrder = this.deliveryOrderRepository.save(deliveryOrder);
        return this.modelMapper.map(updateDeliveryOrder, ResDeliveryOrderDto.class);
    }

    @Override
    public ResDeliveryOrderDto cancelImageConfirmation(Integer deliveryOrderId) {
        DeliveryOrder deliveryOrder = this.deliveryOrderRepository.findById(deliveryOrderId).orElseThrow(() -> new ResourceNotFoundException("DeliveryOrder", "DeliveryId", deliveryOrderId));
        deliveryOrder.setImageConfirmation(null);
        deliveryOrder.setCompleteDate(null);
        deliveryOrder.setIsCompleted(false);
        DeliveryOrder updateDeliveryOrder = this.deliveryOrderRepository.save(deliveryOrder);
        return this.modelMapper.map(updateDeliveryOrder, ResDeliveryOrderDto.class);
    }

    @Override
    public ResDeliveryOrderDto confirmDeliveryOrderShipper(Integer deliveryOrderId) {
        DeliveryOrder deliveryOrder = this.deliveryOrderRepository.findById(deliveryOrderId).orElseThrow(() -> new ResourceNotFoundException("DeliveryOrder", "DeliveryId", deliveryOrderId));
        deliveryOrder.setIsCompleted(true);
        DeliveryOrder updateDeliveryOrder = this.deliveryOrderRepository.save(deliveryOrder);
        return this.modelMapper.map(updateDeliveryOrder, ResDeliveryOrderDto.class);
    }
}
