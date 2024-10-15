package com.kientran.cart_service.repository;

import com.kientran.cart_service.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByAccountId(Integer accountId);
}
