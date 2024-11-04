package com.kientran.order_service.repository;

import com.kientran.order_service.entity.ItemOrdered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemOrderedRepository extends JpaRepository<ItemOrdered, Integer> {
    @Query(value = "select sum(quantity) from item_ordered;", nativeQuery = true)
    Integer getTotalProductBuy();
}