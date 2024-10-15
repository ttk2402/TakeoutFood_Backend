package com.kientran.product_service.service;

import com.kientran.product_service.dto.CategoryDto;
import com.kientran.product_service.dto.ResCategoryDto;
import com.kientran.product_service.dto.TotalCategoryDto;
import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    void deleteCaterogy(Integer categoryId);
    List<CategoryDto> getCategories();
    ResCategoryDto getCategory(Integer categoryId);
    TotalCategoryDto getTotalCategoryInStore();
}
