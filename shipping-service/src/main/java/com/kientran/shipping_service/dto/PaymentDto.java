package com.kientran.shipping_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Integer id;
    private String bankName;
    private String numberCard;
    private String cardHolderName;
}
