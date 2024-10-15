package com.kientran.order_service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderDto {
    private Integer id;
    private Integer accountId;
}
