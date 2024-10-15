package com.kientran.product_service.controller;

import com.kientran.product_service.dto.DiscountDto;
import com.kientran.product_service.response.ApiResponse;
import com.kientran.product_service.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/discount")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @PostMapping("/add")
    public ResponseEntity<DiscountDto> createDiscount(@RequestBody DiscountDto discountDto) {
        DiscountDto createDis = this.discountService.createDiscount(discountDto);
        return new ResponseEntity<>(createDis, HttpStatus.CREATED);
    }

    @DeleteMapping("/{discountId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer discountId) {
        this.discountService.deleteDiscount(discountId);
        return new ResponseEntity<>(new ApiResponse("Discount is deleted successfully!", true),
                HttpStatus.OK);
    }

    @PutMapping("/{discountId}")
    public ResponseEntity<DiscountDto> updateDiscount(@RequestBody DiscountDto discountDto,
                                                      @PathVariable Integer discountId) {
        DiscountDto updatedDiscount = this.discountService.updateDiscount(discountDto, discountId);
        return new ResponseEntity<>(updatedDiscount, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<DiscountDto>> getAllDiscounts() {
        List<DiscountDto> discountDtos = this.discountService.getDiscounts();
        return new ResponseEntity<>(discountDtos, HttpStatus.OK);
    }
}