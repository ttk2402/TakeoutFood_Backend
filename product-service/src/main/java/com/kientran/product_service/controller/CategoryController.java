package com.kientran.product_service.controller;

import com.kientran.product_service.dto.CategoryDto;
import com.kientran.product_service.dto.ResCategoryDto;
import com.kientran.product_service.dto.TotalCategoryDto;
import com.kientran.product_service.response.ApiResponse;
import com.kientran.product_service.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }
    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable Integer catId) {
        CategoryDto updatedCat = this.categoryService.updateCategory(categoryDto, catId);
        return new ResponseEntity<>(updatedCat, HttpStatus.OK);
    }
    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
        this.categoryService.deleteCaterogy(catId);
        return new ResponseEntity<>(new ApiResponse("Category is deleted successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<ResCategoryDto> getCategory(@PathVariable Integer catId) {
        ResCategoryDto catRes = this.categoryService.getCategory(catId);
        return new ResponseEntity<>(catRes, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = this.categoryService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/totalCategory")
    public ResponseEntity<TotalCategoryDto> getTotalCategory() {
        TotalCategoryDto categoryDto = this.categoryService.getTotalCategoryInStore();
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }
}