package com.kientran.order_service.controller;

import com.kientran.order_service.dto.BillDto;
import com.kientran.order_service.dto.RevenueDayDto;
import com.kientran.order_service.dto.RevenueMonthDto;
import com.kientran.order_service.dto.RevenueYearDto;
import com.kientran.order_service.service.BillService;
import com.kientran.order_service.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/bill")
public class BillController {
    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping("/add")
    public ResponseEntity<BillDto> createBill(@RequestBody BillDto billDto) {
        BillDto createBill = this.billService.createBill(billDto);
        return new ResponseEntity<>(createBill, HttpStatus.CREATED);
    }

    @GetMapping("/{billId}")
    public ResponseEntity<BillDto> getBill(@PathVariable Integer billId) {
        BillDto billDto = this.billService.getBill(billId);
        return new ResponseEntity<>(billDto, HttpStatus.OK);
    }

    @DeleteMapping("/{billId}")
    public ResponseEntity<ApiResponse> deleteBillById(@PathVariable Integer billId) {
        this.billService.deleteBill(billId);
        return new ResponseEntity<>(new ApiResponse("Bill is deleted successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<BillDto>> getAllBill() {
        List<BillDto> billDtos = this.billService.getAllBill();
        return new ResponseEntity<>(billDtos, HttpStatus.OK);
    }

    @GetMapping("/revenue/date")
    public ResponseEntity<List<RevenueDayDto>> getRevenue(@RequestParam String startDate, @RequestParam String endDate) {
       List<RevenueDayDto> revenueDayDtos = this.billService.calculateRevenue(startDate, endDate);
       return new ResponseEntity<>(revenueDayDtos, HttpStatus.OK);
    }

    @GetMapping("/revenue/date/latest")
    public ResponseEntity<List<RevenueDayDto>> calculateRevenueLatest() {
        List<RevenueDayDto> revenueDayDtos = billService.calculateRevenueLatest();
        return new ResponseEntity<>(revenueDayDtos, HttpStatus.OK);
    }
    @GetMapping("/revenue/month/latest")
    public ResponseEntity<List<RevenueMonthDto>> calculateRevenueMonthLatest() {
        List<RevenueMonthDto> revenueMonthDtos = billService.calculateRevenueMonthLatest();
        return new ResponseEntity<>(revenueMonthDtos, HttpStatus.OK);
    }

    @GetMapping("/revenue/month")
    public ResponseEntity<List<RevenueMonthDto>> calculateRevenueMonthToMonth(@RequestParam String startDate, @RequestParam String endDate) {
        List<RevenueMonthDto> revenueMonthDtos = billService.calculateRevenueMonthToMonth(startDate, endDate);
        return new ResponseEntity<>(revenueMonthDtos, HttpStatus.OK);
    }

    @GetMapping("/revenue/year/latest")
    public ResponseEntity<List<RevenueYearDto>> calculateRevenueYearLatest() {
        List<RevenueYearDto> revenueYearDtos = billService.calculateRevenueYearLatest();
        return new ResponseEntity<>(revenueYearDtos, HttpStatus.OK);
    }

}