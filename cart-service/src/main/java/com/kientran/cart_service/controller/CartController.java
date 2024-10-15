package com.kientran.cart_service.controller;

import com.kientran.cart_service.dto.CartDto;
import com.kientran.cart_service.dto.ResCartDto;
import com.kientran.cart_service.response.ApiResponse;
import com.kientran.cart_service.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartDto> createCart(@RequestBody CartDto cartDto) {
        CartDto createCart = this.cartService.createCart(cartDto);
        return new ResponseEntity<>(createCart, HttpStatus.CREATED);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCartByID(@PathVariable Integer cartId) {
        CartDto cartDto = this.cartService.getCartByID(cartId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<ResCartDto> getCartByAccountID(@PathVariable Integer accountId) {
        ResCartDto resCartDto = this.cartService.getCartByAccountID(accountId);
        return new ResponseEntity<>(resCartDto, HttpStatus.OK);
    }

    @GetMapping("/find/account/{accountId}")
    public ResponseEntity<CartDto> findCartByAccountID(@PathVariable Integer accountId) {
        CartDto cartDto = this.cartService.findCartByAccountID(accountId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse> deleteCartById(@PathVariable Integer cartId) {
        this.cartService.deleteCart(cartId);
        return new ResponseEntity<>(new ApiResponse("Cart is deleted successfully!", true),
                HttpStatus.OK);
    }

    @DeleteMapping("/account/{accountId}")
    public ResponseEntity<ApiResponse> deleteCartByAccountID(@PathVariable Integer accountId) {
        this.cartService.deleteCartByAccountID(accountId);
        return new ResponseEntity<>(new ApiResponse("Cart is deleted successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CartDto>> getAllProducts() {
        List<CartDto> cartDtos = this.cartService.getCarts();
        return new ResponseEntity<>(cartDtos, HttpStatus.OK);
    }
}