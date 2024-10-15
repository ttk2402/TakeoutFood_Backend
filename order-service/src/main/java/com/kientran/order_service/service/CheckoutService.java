package com.kientran.order_service.service;

import com.kientran.order_service.dto.CheckoutDto;
import java.util.List;

public interface CheckoutService {
    CheckoutDto createCheckOut(CheckoutDto checkOutDto);
    void deleteCheckOut(Integer checkOutId);
    List<CheckoutDto> getCheckOuts();
}
