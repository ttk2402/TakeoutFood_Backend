package com.kientran.cart_service.repository;

import com.kientran.cart_service.entity.Cart;
import com.kientran.cart_service.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByCart(Cart cart);

    @Query(value = "SELECT a.id, a.price, a.product_id, a.quantity, a.cart_id, a.product_name, a.product_price, a.product_image FROM item a, cart b WHERE a.cart_id = b.id AND b.account_id = :accountId;", nativeQuery = true)
    List<Item> findItemsInCartByAccountID(@Param("accountId") Integer accountId);
}

