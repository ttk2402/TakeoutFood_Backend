package com.kientran.order_service.service;

import com.kientran.order_service.dto.DeliveryInformationDto;
import java.util.List;

public interface DeliveryInformationService {
    DeliveryInformationDto create(DeliveryInformationDto deliveryInfoDto);
    void delete(Integer deliveryInfoId);
    List<DeliveryInformationDto> getDeliveryInfos();
}
