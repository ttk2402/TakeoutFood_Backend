package com.kientran.order_service.controller;

import com.kientran.order_service.dto.OrderStatusDto;
import com.kientran.order_service.response.ApiResponse;
import com.kientran.order_service.service.OrderStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/order_status")
public class OrderStatusController {
    private final OrderStatusService orStatusService;

    public OrderStatusController(OrderStatusService orStatusService) {
        this.orStatusService = orStatusService;
    }

    @PostMapping("/add")
    public ResponseEntity<OrderStatusDto> addOrderStatus(@RequestBody OrderStatusDto orStatusDto) {
        OrderStatusDto createOrStatusDto = this.orStatusService.createOrderStatus(orStatusDto);
        return new ResponseEntity<>(createOrStatusDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orStatusId}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable Integer orStatusId) {
        this.orStatusService.deleteOrderStatus(orStatusId);
        return new ResponseEntity<>(new ApiResponse("Order status is deleted successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderStatusDto>> getAllOrderStatus() {
        List<OrderStatusDto> orStatusDtos = this.orStatusService.getOrderStatuses();
        return ResponseEntity.ok(orStatusDtos);
    }
}