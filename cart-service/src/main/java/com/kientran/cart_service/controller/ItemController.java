package com.kientran.cart_service.controller;

import com.kientran.cart_service.dto.ItemDto;
import com.kientran.cart_service.response.ApiResponse;
import com.kientran.cart_service.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/item")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/add/{accountId}")
    public ResponseEntity<ItemDto> createProductItem(@RequestBody ItemDto itemDto,
                                                     @PathVariable Integer accountId) {
        ItemDto createItem = this.itemService.createItem(itemDto, accountId);
        return new ResponseEntity<>(createItem, HttpStatus.CREATED);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@RequestBody ItemDto itemDto,
                                              @PathVariable Integer itemId) {
        ItemDto updatedItem = this.itemService.updateItem(itemDto, itemId);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Integer itemId) {
        this.itemService.deleteItem(itemId);
        return new ResponseEntity<>(new ApiResponse("Item is deleted successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/all/{cartId}")
    public ResponseEntity<List<ItemDto>> getItemByCart(@PathVariable Integer cartId) {
        List<ItemDto> itemDtos = this.itemService.getItemsByCart(cartId);
        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
    }

    @GetMapping("/all/account/{accountId}")
    public ResponseEntity<List<ItemDto>> getItemByAccountID(@PathVariable Integer accountId) {
        List<ItemDto> itemDtos = this.itemService.getItemsByAccountID(accountId);
        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItemByID(@PathVariable Integer itemId) {
        ItemDto itemDto = this.itemService.getItemById(itemId);
        return new ResponseEntity<>(itemDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ItemDto>> getAllItems() {
        List<ItemDto> itemDtos = this.itemService.getAllItems();
        return new ResponseEntity<>(itemDtos, HttpStatus.OK);
    }
}
