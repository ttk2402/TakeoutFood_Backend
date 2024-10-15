package com.kientran.product_service.service.impl;

import com.kientran.product_service.dto.DiscountDto;
import com.kientran.product_service.entity.Discount;
import com.kientran.product_service.exception.ResourceNotFoundException;
import com.kientran.product_service.repository.DiscountRepository;
import com.kientran.product_service.service.DiscountService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;

    public DiscountServiceImpl(DiscountRepository discountRepository, ModelMapper modelMapper) {
        this.discountRepository = discountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DiscountDto createDiscount(DiscountDto discountDto) {
        Discount dis = this.modelMapper.map(discountDto, Discount.class);
        Discount addDis = this.discountRepository.save(dis);
        return this.modelMapper.map(addDis, DiscountDto.class);
    }

    @Override
    public void deleteDiscount(Integer discountId) {
        Discount discount = this.discountRepository.findById(discountId)
                .orElseThrow(() -> new ResourceNotFoundException("Discount", "discountId", discountId));
        this.discountRepository.delete(discount);
    }

    @Override
    public DiscountDto updateDiscount(DiscountDto discountDto, Integer discountId) {
        Discount discount = this.discountRepository.findById(discountId)
                .orElseThrow(() -> new ResourceNotFoundException("Discount ", "discountId", discountId));
        discount.setTitle(discountDto.getTitle());
        discount.setPercent(discountDto.getPercent());
        Discount updateDiscount = this.discountRepository.save(discount);
        return this.modelMapper.map(updateDiscount, DiscountDto.class);
    }

    @Override
    public List<DiscountDto> getDiscounts() {
        List<Discount> discounts = this.discountRepository.findAll();
        return discounts.stream().map((discount) -> this.modelMapper.map(discount, DiscountDto.class))
                .collect(Collectors.toList());
    }
}
