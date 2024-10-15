package com.kientran.order_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ItemOrderedDto {
    private Integer Id;
    private Integer productId;
    private int quantity;
    private Double price;
    private String productName;
    private Double productPrice;
    private String productImage;
}