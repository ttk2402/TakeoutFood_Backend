package com.kientran.shipping_service.service;

import com.kientran.shipping_service.dto.DeliveryOrderDto;
import com.kientran.shipping_service.dto.ResDeliveryOrderDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DeliveryOrderService {
    ResDeliveryOrderDto createDeliveryOrder(DeliveryOrderDto deliveryOrderDto, Integer shipperId);
    ResDeliveryOrderDto getDeliveryOrder(Integer deliveryOrderId);
    Integer deleteDeliveryOrder(Integer deliveryOrderId);
    List<ResDeliveryOrderDto> getAllDeliveryOrder();
    List<ResDeliveryOrderDto> getAllDeliveryOrderCurrentDto();
    List<ResDeliveryOrderDto> getAllDeliveryOrderCompleteDto();
    ResDeliveryOrderDto addImageConfirmation(Integer deliveryOrderId, String imageUrl);
    ResDeliveryOrderDto cancelImageConfirmation(Integer deliveryOrderId);
    ResDeliveryOrderDto confirmDeliveryOrderShipper(Integer deliveryOrderId);
}
