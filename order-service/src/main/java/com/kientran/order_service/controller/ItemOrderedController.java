package com.kientran.order_service.controller;

import com.kientran.order_service.dto.ItemOrderedDto;
import com.kientran.order_service.dto.TotalProductBuyDto;
import com.kientran.order_service.service.ItemOrderedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/itemOrdered")
public class ItemOrderedController {
    private final ItemOrderedService itemOrderedService;

    public ItemOrderedController(ItemOrderedService itemOrderedService) {
        this.itemOrderedService = itemOrderedService;
    }

    @PostMapping("/add/{orderId}")
    public ResponseEntity<ItemOrderedDto> createProductItem(@RequestBody ItemOrderedDto itemOrderedDto,
                                                            @PathVariable Integer orderId) {
        ItemOrderedDto createItem = this.itemOrderedService.createItem(itemOrderedDto, orderId);
        return new ResponseEntity<>(createItem, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<ItemOrderedDto>> getAllItemOrdered() {
        List<ItemOrderedDto> itemOrderedDtos = this.itemOrderedService.getAllItemOrdered();
        return ResponseEntity.ok(itemOrderedDtos);
    }

    @GetMapping("/totalProductBuy")
    public ResponseEntity<TotalProductBuyDto> getTotalOrder() {
        TotalProductBuyDto productBuyDto = this.itemOrderedService.getTotalProductBuy();
        return new ResponseEntity<>(productBuyDto, HttpStatus.OK);
    }

}
