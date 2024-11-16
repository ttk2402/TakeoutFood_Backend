package com.kientran.order_service.webclient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ResItemDto {
    private Integer Id;
    private Integer productId;
    private int quantity;
    private Double price;
    private String productName;
    private Double productPrice;
    private String productImage;
}
