package com.kientran.order_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DeliveryInformationDto {
    private Integer id;
    private String recipientName;
    private String phoneNumber;
    private String commune;
    private String district;
    private String province;
    private String description;
}
