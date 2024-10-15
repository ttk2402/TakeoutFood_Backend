package com.kientran.order_service.controller;

import com.kientran.order_service.dto.OrderDto;
import com.kientran.order_service.dto.ResOrderDto;
import com.kientran.order_service.dto.TotalOrderDto;
import com.kientran.order_service.response.ApiResponse;
import com.kientran.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add/{checkoutId}")
    public ResponseEntity<ResOrderDto> addOrderByStaff(@RequestBody OrderDto orderDto,
                                                   @PathVariable Integer checkoutId) {
        ResOrderDto createOrder = this.orderService.createOrderByStaff(orderDto, checkoutId);
        return new ResponseEntity<>(createOrder, HttpStatus.CREATED);
    }

    @PostMapping("/add/{checkoutId}/{deliveryInfoId}")
    public ResponseEntity<ResOrderDto> addOrderByCustomer(@RequestBody OrderDto orderDto,
                                                @PathVariable Integer checkoutId,
                                                @PathVariable Integer deliveryInfoId) {
        ResOrderDto createOrder = this.orderService.createOrderByCustomer(orderDto, checkoutId, deliveryInfoId);
        return new ResponseEntity<>(createOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ResOrderDto> getOrder(@PathVariable Integer orderId) {
        ResOrderDto orderDto = this.orderService.getOrder(orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> deleteOrderById(@PathVariable Integer orderId) {
        this.orderService.deleteOrder(orderId);
        return new ResponseEntity<>(new ApiResponse("Order is deleted successfully!", true),
                HttpStatus.OK);
    }

    @PutMapping("/{orderId}/{orderStatusId}")
    public ResponseEntity<ResOrderDto> changeOrderStatusById(@PathVariable Integer orderId,
                                                             @PathVariable Integer orderStatusId) {
        ResOrderDto updatedOr = this.orderService.changeStatusOfOrder(orderId, orderStatusId);
        return new ResponseEntity<>(updatedOr, HttpStatus.OK);
    }

    @GetMapping("/orderStatus/{orderStatusId}")
    public ResponseEntity<List<ResOrderDto>> getOrdersByOrderStatusID(@PathVariable Integer orderStatusId) {
        List<ResOrderDto> orderDtos = this.orderService.getOrdersByOrderStatus(orderStatusId);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ResOrderDto>> getAllOrder() {
        List<ResOrderDto> orderDtos = this.orderService.getAllOrder();
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<ResOrderDto>> getAllOrderByAccountID(@PathVariable Integer accountId) {
        List<ResOrderDto> orderDtos = this.orderService.getAllOrderOfAccountID(accountId);
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @GetMapping("/totalOrder")
    public ResponseEntity<TotalOrderDto> getTotalOrder() {
        TotalOrderDto orderDto = this.orderService.getTotalOrderInStore();
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }
}