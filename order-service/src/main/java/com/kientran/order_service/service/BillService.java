package com.kientran.order_service.service;

import com.kientran.order_service.dto.BillDto;
import com.kientran.order_service.dto.RevenueDayDto;
import com.kientran.order_service.dto.RevenueMonthDto;
import com.kientran.order_service.dto.RevenueYearDto;

import java.util.List;

public interface BillService {
    BillDto createBill(BillDto billDto);
    BillDto getBill(Integer billId);
    void deleteBill(Integer billId);
    List<BillDto> getAllBill();
    List<RevenueDayDto> calculateRevenue(String startDate, String endDate);
    List<RevenueDayDto> calculateRevenueLatest();
    List<RevenueMonthDto> calculateRevenueMonthLatest();
    List<RevenueMonthDto> calculateRevenueMonthToMonth(String startDate, String endDate);
    List<RevenueYearDto> calculateRevenueYearLatest();
}
