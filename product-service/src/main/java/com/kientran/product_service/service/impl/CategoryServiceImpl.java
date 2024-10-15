package com.kientran.product_service.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import com.kientran.product_service.dto.CategoryDto;
import com.kientran.product_service.dto.ResCategoryDto;
import com.kientran.product_service.dto.TotalCategoryDto;
import com.kientran.product_service.entity.Category;
import com.kientran.product_service.exception.ResourceNotFoundException;
import com.kientran.product_service.repository.CategoryRepository;
import com.kientran.product_service.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = this.modelMapper.map(categoryDto, Category.class);
        Category addCat = this.categoryRepository.save(cat);
        return this.modelMapper.map(addCat, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId", categoryId));
        cat.setTitle(categoryDto.getTitle());
        cat.setUrl_image_category(categoryDto.getUrl_image_category());
        Category updatedcat = this.categoryRepository.save(cat);
        return this.modelMapper.map(updatedcat, CategoryDto.class);
    }
    @Override
    public void deleteCaterogy(Integer categoryId) {
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId", categoryId));
        this.categoryRepository.delete(cat);

    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = this.categoryRepository.findAll();
        return categories.stream().map((cat)-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public ResCategoryDto getCategory(Integer categoryId) {
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","CategoryId", categoryId));
        return this.modelMapper.map(cat, ResCategoryDto.class);
    }

    @Override
    public TotalCategoryDto getTotalCategoryInStore() {
        TotalCategoryDto categoryDto = new TotalCategoryDto();
        categoryDto.setTotal(this.categoryRepository.getTotalCategory());
        return categoryDto;
    }
}
