package com.kientran.shipping_service.dto;

import com.kientran.shipping_service.entity.Shipper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResDeliveryOrderDto {
    private Integer id;
    private Integer orderId;
    private String receiveDate;
    private String completeDate;
    private String imageConfirmation;
    private Boolean isCompleted;
    private Shipper shipper;
}