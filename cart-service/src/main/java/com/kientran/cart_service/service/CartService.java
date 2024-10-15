package com.kientran.cart_service.service;

import com.kientran.cart_service.dto.CartDto;
import com.kientran.cart_service.dto.ResCartDto;
import java.util.List;

public interface CartService {
    CartDto createCart(CartDto cartDto);
    CartDto getCartByID(Integer cartId);
    ResCartDto getCartByAccountID(Integer accountId);
    CartDto findCartByAccountID(Integer accountId);
    void deleteCart(Integer cartId);
    List<CartDto> getCarts();
    void deleteCartByAccountID(Integer accountId);
}
