package com.kientran.shipping_service.service.impl;

import com.kientran.shipping_service.dto.PaymentDto;
import com.kientran.shipping_service.entity.Payment;
import com.kientran.shipping_service.exception.ResourceNotFoundException;
import com.kientran.shipping_service.repository.PaymentRepository;
import com.kientran.shipping_service.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        Payment payment = this.modelMapper.map(paymentDto, Payment.class);
        Payment addPayment = this.paymentRepository.save(payment);
        return this.modelMapper.map(addPayment, PaymentDto.class);
    }

    @Override
    public PaymentDto getPayment(Integer paymentId) {
        Payment payment = this.paymentRepository.findById(paymentId).orElseThrow(()-> new ResourceNotFoundException("Payment","PaymentId", paymentId));
        return this.modelMapper.map(payment, PaymentDto.class);
    }

    @Override
    public PaymentDto updatePayment(PaymentDto paymentDto, Integer paymentId) {
        Payment payment = this.paymentRepository.findById(paymentId).orElseThrow(()-> new ResourceNotFoundException("Payment","PaymentId", paymentId));
        payment.setBankName(paymentDto.getBankName());
        payment.setNumberCard(paymentDto.getNumberCard());
        payment.setCardHolderName(paymentDto.getCardHolderName());
        Payment updatedPayment = this.paymentRepository.save(payment);
        return this.modelMapper.map(updatedPayment, PaymentDto.class);
    }

    @Override
    public void deletePayment(Integer paymentId) {
        Payment payment = this.paymentRepository.findById(paymentId).orElseThrow(()-> new ResourceNotFoundException("Payment","PaymentId", paymentId));
        this.paymentRepository.delete(payment);
    }

    @Override
    public List<PaymentDto> getAllPayment() {
        List<Payment> payments = this.paymentRepository.findAll();
        return payments.stream().map((payment) -> this.modelMapper.map(payment, PaymentDto.class))
                .collect(Collectors.toList());
    }
}
