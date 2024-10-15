package com.kientran.product_service.repository;

import com.kientran.product_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "select count(*) from category;", nativeQuery = true)
    Integer getTotalCategory();
}
