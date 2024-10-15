package com.kientran.order_service.service;

import com.kientran.order_service.dto.BillDto;
import java.util.List;

public interface BillService {
    BillDto createBill(BillDto billDto);
    BillDto getBill(Integer billId);
    void deleteBill(Integer billId);
    List<BillDto> getAllBill();
}
