package com.kientran.product_service.repository;

import com.kientran.product_service.entity.Category;
import com.kientran.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);

    @Query(value = "select id from product", nativeQuery = true)
    List<Integer> getAllIdOfProduct();

    @Query(value = "select count(*) from product;", nativeQuery = true)
    Integer getTotalProduct();
}
