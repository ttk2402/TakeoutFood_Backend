package com.kientran.order_service.service.impl;

import com.kientran.order_service.dto.BillDto;
import com.kientran.order_service.entity.Bill;
import com.kientran.order_service.exception.ResourceNotFoundException;
import com.kientran.order_service.repository.BillRepository;
import com.kientran.order_service.service.BillService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final ModelMapper modelMapper;

    public BillServiceImpl(BillRepository billRepository, ModelMapper modelMapper) {
        this.billRepository = billRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public BillDto createBill(BillDto billDto) {
        Bill bill = this.modelMapper.map(billDto, Bill.class);
        Bill addBill = this.billRepository.save(bill);
        return this.modelMapper.map(addBill, BillDto.class);
    }

    @Override
    public BillDto getBill(Integer billId) {
        Bill bill = this.billRepository.findById(billId).orElseThrow(()-> new ResourceNotFoundException("Bill","BillId", billId));
        return this.modelMapper.map(bill, BillDto.class);
    }

    @Override
    public void deleteBill(Integer billId) {
        Bill bill = this.billRepository.findById(billId).orElseThrow(()-> new ResourceNotFoundException("Bill","BillId", billId));
        this.billRepository.delete(bill);
    }

    @Override
    public List<BillDto> getAllBill() {
        List<Bill> bills = this.billRepository.findAll();
        List<BillDto> billDtos = bills.stream().map((bill) -> this.modelMapper.map(bill, BillDto.class))
                .collect(Collectors.toList());
        return billDtos;
    }
}
