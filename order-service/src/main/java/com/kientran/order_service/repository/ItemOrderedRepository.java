package com.kientran.order_service.repository;

import com.kientran.order_service.entity.ItemOrdered;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOrderedRepository extends JpaRepository<ItemOrdered, Integer> {}