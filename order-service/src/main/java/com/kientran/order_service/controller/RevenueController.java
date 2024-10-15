package com.kientran.order_service.controller;

import com.kientran.order_service.dto.RevenueDto;
import com.kientran.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/revenue")
public class RevenueController {
    private final OrderService orderService;

    public RevenueController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public ResponseEntity<RevenueDto> getTotalRevenue() {
        RevenueDto revenueDto = this.orderService.getRevenueOfStore();
        return new ResponseEntity<>(revenueDto, HttpStatus.OK);
    }
}
