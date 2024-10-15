package com.kientran.product_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DiscountDto {
    private Integer id;
    private String title;
    private Double percent;
}
