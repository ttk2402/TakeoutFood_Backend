package com.kientran.shipping_service.service.impl;

import com.kientran.shipping_service.dto.ResShipperDto;
import com.kientran.shipping_service.dto.ShipperDto;
import com.kientran.shipping_service.dto.UpdateShipperDto;
import com.kientran.shipping_service.entity.Payment;
import com.kientran.shipping_service.entity.Shipper;
import com.kientran.shipping_service.exception.ResourceNotFoundException;
import com.kientran.shipping_service.repository.PaymentRepository;
import com.kientran.shipping_service.repository.ShipperRepository;
import com.kientran.shipping_service.service.ShipperService;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShipperServiceImpl implements ShipperService {

    private final ShipperRepository shipperRepository;
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    public ShipperServiceImpl(ShipperRepository shipperRepository, PaymentRepository paymentRepository, ModelMapper modelMapper) {
        this.shipperRepository = shipperRepository;
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
    }

    @KafkaListener(topics = "add_shipper_topic")
    public void consume_to_add_shipper(String message) {
        System.out.println("Message received from identity-service to add new shipper: " + message);
        ShipperDto shipperDto = new ShipperDto();
        Integer accountId = Integer.parseInt(message);
        shipperDto.setAccountId(accountId);
        createShipper(shipperDto);
    }

    @KafkaListener(topics = "delete_shipper_topic")
    public void consume_to_delete_shipper(String message) {
        System.out.println("Message received from identity-service to remove shipper: " + message);
        Integer accountId = Integer.parseInt(message);
        ResShipperDto resShipperDto = getShipperByAccountID(accountId);
        deleteShipper(resShipperDto.getId());
    }

    @Override
    public ResShipperDto createShipper(ShipperDto shipperDto) {
        Shipper shipper = this.modelMapper.map(shipperDto, Shipper.class);
        shipper.setAmount(0.0);
        Shipper addShipper = this.shipperRepository.save(shipper);
        return this.modelMapper.map(addShipper, ResShipperDto.class);
    }

    @Override
    public ResShipperDto getShipper(Integer shipperId) {
        Shipper shipper = this.shipperRepository.findById(shipperId).orElseThrow(() -> new ResourceNotFoundException("Shipper", "ShipperId", shipperId));
        return this.modelMapper.map(shipper, ResShipperDto.class);
    }

    @Override
    public ResShipperDto getShipperByAccountID(Integer accountId) {
        Shipper shipper = this.shipperRepository.findByAccountId(accountId).orElseThrow(() -> new ResourceNotFoundException("Shipper", "AccountId", accountId));
        return this.modelMapper.map(shipper, ResShipperDto.class);
    }

    @Override
    public ResShipperDto addPaymentInfo(Integer shipperId, Integer paymentId) {
        Shipper shipper = this.shipperRepository.findById(shipperId).orElseThrow(() -> new ResourceNotFoundException("Shipper", "ShipperId", shipperId));
        Payment payment = this.paymentRepository.findById(paymentId).orElseThrow(() -> new ResourceNotFoundException("Payment", "PaymentId", paymentId));
        shipper.setPayment(payment);
        Shipper updateShipper = this.shipperRepository.save(shipper);
        return this.modelMapper.map(updateShipper, ResShipperDto.class);
    }

    @Override
    public ResShipperDto updateShipper(UpdateShipperDto updateShipperDto, Integer shipperId) {
        Shipper shipper = this.shipperRepository.findById(shipperId).orElseThrow(() -> new ResourceNotFoundException("Shipper", "ShipperId", shipperId));
        shipper.setPhoneNumber(updateShipperDto.getPhoneNumber());
        shipper.setCredentialCode(updateShipperDto.getCredentialCode());
        Shipper updatedShipper = this.shipperRepository.save(shipper);
        return this.modelMapper.map(updatedShipper, ResShipperDto.class);
    }

    @Override
    public void resetIncome(Integer shipperId) {
        Shipper shipper = this.shipperRepository.findById(shipperId).orElseThrow(() -> new ResourceNotFoundException("Shipper", "ShipperId", shipperId));
        shipper.setAmount(0.0);
        this.shipperRepository.save(shipper);
    }

    @Override
    public void updateIncome(Integer shipperId) {
        Shipper shipper = this.shipperRepository.findById(shipperId).orElseThrow(() -> new ResourceNotFoundException("Shipper", "ShipperId", shipperId));
        shipper.setAmount(shipper.getAmount() + 15000.0);
        this.shipperRepository.save(shipper);
    }

    @Override
    public void downIncome(Integer shipperId) {
        Shipper shipper = this.shipperRepository.findById(shipperId).orElseThrow(() -> new ResourceNotFoundException("Shipper", "ShipperId", shipperId));
        shipper.setAmount(shipper.getAmount() - 15000.0);
        this.shipperRepository.save(shipper);
    }

    @Override
    public void deleteShipper(Integer shipperId) {
        Shipper shipper = this.shipperRepository.findById(shipperId).orElseThrow(() -> new ResourceNotFoundException("Shipper", "ShipperId", shipperId));
        this.shipperRepository.delete(shipper);
    }

    @Override
    public List<ResShipperDto> getAllShipper() {
        List<Shipper> shippers = this.shipperRepository.findAll();
        return shippers.stream().map((shipper) -> this.modelMapper.map(shipper, ResShipperDto.class))
                .collect(Collectors.toList());
    }
}
