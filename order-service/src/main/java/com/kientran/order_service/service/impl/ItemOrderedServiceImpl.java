package com.kientran.order_service.service.impl;

import com.kientran.order_service.dto.ItemOrderedDto;
import com.kientran.order_service.dto.TotalProductBuyDto;
import com.kientran.order_service.entity.ItemOrdered;
import com.kientran.order_service.entity.Order;
import com.kientran.order_service.exception.ResourceNotFoundException;
import com.kientran.order_service.repository.ItemOrderedRepository;
import com.kientran.order_service.repository.OrderRepository;
import com.kientran.order_service.service.ItemOrderedService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemOrderedServiceImpl implements ItemOrderedService {
    private final ItemOrderedRepository itemOrderedRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public ItemOrderedServiceImpl(ItemOrderedRepository itemOrderedRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.itemOrderedRepository = itemOrderedRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ItemOrderedDto createItem(ItemOrderedDto itemOrderedDto, Integer orderId) {
        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order ", "OrderId", orderId));
        ItemOrdered itemOrdered = this.modelMapper.map(itemOrderedDto, ItemOrdered.class);
        itemOrdered.setOrder(order);
        ItemOrdered newItemOrdered = this.itemOrderedRepository.save(itemOrdered);
        return this.modelMapper.map(newItemOrdered, ItemOrderedDto.class);
    }

    @Override
    public List<ItemOrderedDto> getAllItemOrdered() {
        List<ItemOrdered> itemOrdereds = this.itemOrderedRepository.findAll();
        return itemOrdereds.stream().map((itemOrdered)-> this.modelMapper.map(itemOrdered, ItemOrderedDto.class)).collect(Collectors.toList());
    }

    @Override
    public TotalProductBuyDto getTotalProductBuy() {
        TotalProductBuyDto productBuyDto = new TotalProductBuyDto();
        productBuyDto.setTotal(this.itemOrderedRepository.getTotalProductBuy());
        return productBuyDto;
    }
}
