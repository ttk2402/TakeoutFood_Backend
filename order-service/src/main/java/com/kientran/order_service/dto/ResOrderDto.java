package com.kientran.order_service.dto;

import com.kientran.order_service.entity.Checkout;
import com.kientran.order_service.entity.ItemOrdered;
import com.kientran.order_service.entity.DeliveryInformation;
import com.kientran.order_service.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ResOrderDto {
    private Integer id;
    private Integer accountId;
    private Double totalprice;
    private String date;
    private Checkout checkout;
    private OrderStatus orderStatus;
    private DeliveryInformation deliveryInformation;
    private List<ItemOrdered> itemOrdereds;
}