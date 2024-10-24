package com.kientran.shipping_service.controller;

import com.kientran.shipping_service.dto.PaymentDto;
import com.kientran.shipping_service.response.ApiResponse;
import com.kientran.shipping_service.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/add")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto) {
        PaymentDto createPayment = this.paymentService.createPayment(paymentDto);
        return new ResponseEntity<>(createPayment, HttpStatus.CREATED);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable Integer paymentId) {
        PaymentDto paymentDto = this.paymentService.getPayment(paymentId);
        return new ResponseEntity<>(paymentDto, HttpStatus.OK);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentDto> updatePayment(@RequestBody PaymentDto paymentDto,
                                                      @PathVariable Integer paymentId) {
       PaymentDto updatedPayment = this.paymentService.updatePayment(paymentDto, paymentId);
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> deletePaymentById(@PathVariable Integer paymentId) {
        this.paymentService.deletePayment(paymentId);
        return new ResponseEntity<>(new ApiResponse("Payment is deleted successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<PaymentDto>> getAllPayment() {
        List<PaymentDto> paymentDtos = this.paymentService.getAllPayment();
        return new ResponseEntity<>(paymentDtos, HttpStatus.OK);
    }
}
