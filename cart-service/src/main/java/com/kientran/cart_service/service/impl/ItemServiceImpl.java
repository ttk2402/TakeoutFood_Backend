package com.kientran.cart_service.service.impl;

import com.kientran.cart_service.dto.ItemDto;
import com.kientran.cart_service.entity.Cart;
import com.kientran.cart_service.entity.Item;
import com.kientran.cart_service.exception.ResourceNotFoundException;
import com.kientran.cart_service.repository.CartRepository;
import com.kientran.cart_service.repository.ItemRepository;
import com.kientran.cart_service.service.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    public ItemServiceImpl(ItemRepository itemRepository, CartRepository cartRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
    }

    @KafkaListener(topics = "delete_item_topic")
    public void consume_to_delete_item(String message) {
        System.out.println("Message received from order-service to remove item: " + message);
        Integer itemId = Integer.parseInt(message);
        deleteItem(itemId);
    }

    @Override
    public ItemDto createItem(ItemDto itemDto, Integer accountId) {
        Cart cart = this.cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart ", "AccountId", accountId));
        Item item = this.modelMapper.map(itemDto, Item.class);
        item.setCart(cart);
        Item newItem = this.itemRepository.save(item);
        return this.modelMapper.map(newItem, ItemDto.class);
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, Integer itemId) {
        Item item = this.itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item ", "ItemId", itemId));
        item.setQuantity(itemDto.getQuantity());
        item.setPrice(itemDto.getPrice());
        Item updateItem = this.itemRepository.save(item);
        return this.modelMapper.map(updateItem, ItemDto.class);
    }

    @Override
    public void deleteItem(Integer itemId) {
        Item item = this.itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item", "ItemId", itemId));
        this.itemRepository.delete(item);
    }

    @Override
    public List<ItemDto> getItemsByCart(Integer cartId) {
        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));
        List<Item> items = this.itemRepository.findByCart(cart);
        List<ItemDto> itemDtos = items.stream().map((item) -> this.modelMapper.map(item, ItemDto.class))
                .collect(Collectors.toList());
        return itemDtos;
    }

    @Override
    public List<ItemDto> getItemsByAccountID(Integer accountId) {
        List<Item> items = this.itemRepository.findItemsInCartByAccountID(accountId);
        List<ItemDto> itemDtos = items.stream().map((item) -> this.modelMapper.map(item, ItemDto.class))
                .collect(Collectors.toList());
        return itemDtos;
    }

    @Override
    public ItemDto getItemById(Integer itemId) {
        Item item = this.itemRepository.findById(itemId).orElseThrow(()-> new ResourceNotFoundException("Item","ItemId", itemId));
        return this.modelMapper.map(item, ItemDto.class);
    }

    @Override
    public List<ItemDto> getAllItems() {
        List<Item> items = this.itemRepository.findAll();
        List<ItemDto> itemDtos = items.stream().map((item) -> this.modelMapper.map(item, ItemDto.class))
                .collect(Collectors.toList());
        return itemDtos;
    }
}
