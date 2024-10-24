package com.kientran.shipping_service.service;

import com.kientran.shipping_service.dto.PaymentDto;
import java.util.List;

public interface PaymentService {
    PaymentDto createPayment(PaymentDto paymentDto);
    PaymentDto getPayment(Integer paymentId);
    PaymentDto updatePayment(PaymentDto paymentDto, Integer paymentId);
    void deletePayment(Integer paymentId);
    List<PaymentDto> getAllPayment();
}
