package com.kientran.shipping_service.controller;

import com.kientran.shipping_service.dto.ResShipperDto;
import com.kientran.shipping_service.dto.ShipperDto;
import com.kientran.shipping_service.dto.UpdateShipperDto;
import com.kientran.shipping_service.response.ApiResponse;
import com.kientran.shipping_service.service.ShipperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/shipper")
public class ShipperController {

    private final ShipperService shipperService;

    public ShipperController(ShipperService shipperService) {
        this.shipperService = shipperService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResShipperDto> createShippper(@RequestBody ShipperDto shipperDto) {
        ResShipperDto createShipper = this.shipperService.createShipper(shipperDto);
        return new ResponseEntity<>(createShipper, HttpStatus.CREATED);
    }

    @GetMapping("/{shipperId}")
    public ResponseEntity<ResShipperDto> getShipper(@PathVariable Integer shipperId) {
        ResShipperDto resShipperDto = this.shipperService.getShipper(shipperId);
        return new ResponseEntity<>(resShipperDto, HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ResShipperDto> getShipperByAccountID(@PathVariable Integer accountId) {
        ResShipperDto resShipperDto = this.shipperService.getShipperByAccountID(accountId);
        return new ResponseEntity<>(resShipperDto, HttpStatus.OK);
    }

    @PutMapping("/{shipperId}")
    public ResponseEntity<ResShipperDto> updateShipper(@RequestBody UpdateShipperDto updateShipperDto,
                                                       @PathVariable Integer shipperId) {
        ResShipperDto updatedShipper = this.shipperService.updateShipper(updateShipperDto, shipperId);
        return new ResponseEntity<>(updatedShipper, HttpStatus.OK);
    }

    @PutMapping("/downAmount/{shipperId}")
    public ResponseEntity<ApiResponse> downIncome(@PathVariable Integer shipperId) {
        this.shipperService.downIncome(shipperId);
        return new ResponseEntity<>(new ApiResponse("Amount of shipper is updated successfully!", true),
                HttpStatus.OK);
    }

    @PutMapping("/{shipperId}/{paymentId}")
    public ResponseEntity<ResShipperDto> updateShipper(@PathVariable Integer shipperId,
                                                       @PathVariable Integer paymentId) {
        ResShipperDto updatedShipper = this.shipperService.addPaymentInfo(shipperId, paymentId);
        return new ResponseEntity<>(updatedShipper, HttpStatus.OK);
    }

    @DeleteMapping("/{shipperId}")
    public ResponseEntity<ApiResponse> deleteShipperById(@PathVariable Integer shipperId) {
        this.shipperService.deleteShipper(shipperId);
        return new ResponseEntity<>(new ApiResponse("Shipper is deleted successfully!", true),
                HttpStatus.OK);
    }

    @PutMapping("/resetIncome/{shipperId}")
    public ResponseEntity<ApiResponse> resetIncomeShipper(@PathVariable Integer shipperId) {
        this.shipperService.resetIncome(shipperId);
        return new ResponseEntity<>(new ApiResponse("Amount is updated successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ResShipperDto>> getAllShipper() {
        List<ResShipperDto> shipperDtos = this.shipperService.getAllShipper();
        return new ResponseEntity<>(shipperDtos, HttpStatus.OK);
    }
}
