package com.kientran.product_service.service;

import com.kientran.product_service.dto.ProductDto;
import com.kientran.product_service.dto.TotalProductDto;
import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto, Integer categoryId);
    ProductDto updateProduct(ProductDto productDto, Integer productId);
    void deleteProduct(Integer productId);
    ProductDto getProductById(Integer productId);
    List<ProductDto> getAllProducts();
    ProductDto applyDiscount(Integer productId, Integer discountId);
    ProductDto removeDiscount(Integer productId);
    List<ProductDto> getProductsByCategory(Integer categoryId);
    TotalProductDto getTotalProductInStore();
    List<ProductDto> getProductsHaveDiscount();
}
