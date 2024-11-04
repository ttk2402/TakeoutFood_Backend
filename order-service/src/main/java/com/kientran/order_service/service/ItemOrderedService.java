package com.kientran.order_service.service;

import com.kientran.order_service.dto.ItemOrderedDto;
import com.kientran.order_service.dto.TotalProductBuyDto;

import java.util.List;

public interface ItemOrderedService {
    ItemOrderedDto createItem(ItemOrderedDto itemOrderedDto, Integer orderId);
    List<ItemOrderedDto> getAllItemOrdered();
    TotalProductBuyDto getTotalProductBuy();
}
