package com.kientran.cart_service.service;

import com.kientran.cart_service.dto.ItemDto;
import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemDto itemDto, Integer cartId);
    ItemDto updateItem(ItemDto itemDto, Integer itemId);
    void deleteItem(Integer itemId);
    List<ItemDto> getItemsByCart(Integer cartId);
    List<ItemDto> getItemsByAccountID(Integer accountId);
    ItemDto getItemById(Integer itemId);
    List<ItemDto> getAllItems();
}
