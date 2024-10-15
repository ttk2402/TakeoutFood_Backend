package com.kientran.order_service.controller;

import com.kientran.order_service.dto.DeliveryInformationDto;
import com.kientran.order_service.response.ApiResponse;
import com.kientran.order_service.service.DeliveryInformationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/delivery_information")
public class DeliveryInformationController {
    private final DeliveryInformationService deliveryInfoService;

    public DeliveryInformationController(DeliveryInformationService deliveryInfoService) {
        this.deliveryInfoService = deliveryInfoService;
    }

    @PostMapping("/add")
    public ResponseEntity<DeliveryInformationDto> addDeliveryInfo(@RequestBody DeliveryInformationDto deliveryInfoDto) {
        DeliveryInformationDto createDeliveryInfo = this.deliveryInfoService.create(deliveryInfoDto);
        return new ResponseEntity<>(createDeliveryInfo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{deliveryInfoId}")
    public ResponseEntity<ApiResponse> deleteDeliveryInfo(@PathVariable Integer deliveryInfoId) {
        this.deliveryInfoService.delete(deliveryInfoId);
        return new ResponseEntity<>(new ApiResponse("Location is deleted successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<DeliveryInformationDto>> getAllDeliveryInfos() {
        List<DeliveryInformationDto> deliveryInfoDtos = this.deliveryInfoService.getDeliveryInfos();
        return ResponseEntity.ok(deliveryInfoDtos);
    }
}