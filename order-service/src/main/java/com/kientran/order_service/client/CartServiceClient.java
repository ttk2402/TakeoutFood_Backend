package com.kientran.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "CartService", url = "http://localhost:8083")
public interface CartServiceClient {
    @GetMapping("/api/item/all/account/{accountId}")
    List<ResItemDto> getItemByAccountID(@PathVariable Integer accountId);

    @DeleteMapping("/api/item/{itemId}")
    void deleteItem(@PathVariable Integer itemId);

    @GetMapping("/api/cart/find/account/{accountId}")
    CartDto findCartByAccountID(@PathVariable Integer accountId);
}
