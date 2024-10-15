package com.kientran.cart_service.service.impl;

import com.kientran.cart_service.dto.CartDto;
import com.kientran.cart_service.dto.ResCartDto;
import com.kientran.cart_service.entity.Cart;
import com.kientran.cart_service.exception.ResourceNotFoundException;
import com.kientran.cart_service.repository.CartRepository;
import com.kientran.cart_service.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
    }

    @KafkaListener(topics = "add_account_topic")
    public void consume_to_add_cart(String message) {
        System.out.println("Message received from identity-service to add new cart: " + message);
        CartDto cartDto = new CartDto();
        Integer accountId = Integer.parseInt(message);
        cartDto.setAccountId(accountId);
        createCart(cartDto);
    }

    @KafkaListener(topics = "delete_account_topic")
    public void consume_to_delete_cart(String message) {
        System.out.println("Message received from identity-service to remove cart: " + message);
        Integer accountId = Integer.parseInt(message);
        deleteCartByAccountID(accountId);
    }

    @Override
    public CartDto createCart(CartDto cartDto) {
        Cart cart = this.modelMapper.map(cartDto, Cart.class);
        Cart addCart = this.cartRepository.save(cart);
        return this.modelMapper.map(addCart, CartDto.class);
    }

    @Override
    public CartDto getCartByID(Integer cartId) {
        Cart cart = this.cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart","CartId", cartId));
        return this.modelMapper.map(cart, CartDto.class);
    }

    @Override
    public ResCartDto getCartByAccountID(Integer accountId) {
        Cart cart = this.cartRepository.findByAccountId(accountId).orElseThrow(()-> new ResourceNotFoundException("Cart","AccountID", accountId));
        return this.modelMapper.map(cart, ResCartDto.class);
    }

    @Override
    public CartDto findCartByAccountID(Integer accountId) {
        Cart cart = this.cartRepository.findByAccountId(accountId).orElseThrow(()-> new ResourceNotFoundException("Cart","AccountID", accountId));
        return this.modelMapper.map(cart, CartDto.class);
    }

    @Override
    public void deleteCart(Integer cartId) {
        Cart cart = this.cartRepository.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart","CartId", cartId));
        this.cartRepository.delete(cart);
    }

    @Override
    public List<CartDto> getCarts() {
        List<Cart> carts = this.cartRepository.findAll();
        List<CartDto> cartDtos = carts.stream().map((cart) -> this.modelMapper.map(cart, CartDto.class))
                .collect(Collectors.toList());
        return cartDtos;
    }

    @Override
    public void deleteCartByAccountID(Integer accountId) {
        Cart cart = this.cartRepository.findByAccountId(accountId).orElseThrow(()-> new ResourceNotFoundException("Cart","AccountID", accountId));
        this.cartRepository.delete(cart);
    }
}
