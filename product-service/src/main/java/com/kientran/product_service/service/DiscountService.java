package com.kientran.product_service.service;

import com.kientran.product_service.dto.DiscountDto;
import java.util.List;

public interface DiscountService {
    DiscountDto createDiscount(DiscountDto discountDto);
    List<DiscountDto> getDiscounts();
    void deleteDiscount(Integer discountId);
    DiscountDto updateDiscount(DiscountDto discountDto, Integer discountId);
}
