package com.kientran.shipping_service.dto;

import com.kientran.shipping_service.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResShipperDto {
    private Integer id;
    private Integer accountId;
    private String phoneNumber;
    private String credentialCode;
    private Double amount;
    private Payment payment;
}
