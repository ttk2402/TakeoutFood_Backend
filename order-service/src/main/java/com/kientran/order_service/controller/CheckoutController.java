package com.kientran.order_service.controller;

import com.kientran.order_service.dto.CheckoutDto;
import com.kientran.order_service.response.ApiResponse;
import com.kientran.order_service.service.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/add")
    public ResponseEntity<CheckoutDto> addCheckOut(@RequestBody CheckoutDto checkOutDto) {
        CheckoutDto createCheckOut = this.checkoutService.createCheckOut(checkOutDto);
        return new ResponseEntity<>(createCheckOut, HttpStatus.CREATED);
    }

    @DeleteMapping("/{checkOutId}")
    public ResponseEntity<ApiResponse> deleteCheckOut(@PathVariable Integer checkOutId) {
        this.checkoutService.deleteCheckOut(checkOutId);
        return new ResponseEntity<>(new ApiResponse("Checkout is deleted successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CheckoutDto>> getAllCheckOuts() {
        List<CheckoutDto> checkOuts = this.checkoutService.getCheckOuts();
        return ResponseEntity.ok(checkOuts);
    }
}
