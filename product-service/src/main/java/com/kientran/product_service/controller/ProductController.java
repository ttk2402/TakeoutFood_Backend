package com.kientran.product_service.controller;

import com.kientran.product_service.dto.ProductDto;
import com.kientran.product_service.dto.TotalProductDto;
import com.kientran.product_service.response.ApiResponse;
import com.kientran.product_service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add/{categoryId}")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto,
                                                    @PathVariable Integer categoryId) {
        ProductDto createProduct = this.productService.createProduct(productDto, categoryId);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer productId) {
        this.productService.deleteProduct(productId);
        return new ResponseEntity<>(new ApiResponse("Product is deleted successfully!", true),
                HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Integer productId) {
        ProductDto productDto = this.productService.getProductById(productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
                                                    @PathVariable Integer productId) {
        ProductDto updatedProduct = this.productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = this.productService.getAllProducts();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @PutMapping("/applyDiscount/{productId}/{discountId}")
    public ResponseEntity<ProductDto> applyProduct(@PathVariable Integer productId,
                                                    @PathVariable Integer discountId) {
        ProductDto updatedProduct = this.productService.applyDiscount(productId, discountId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PutMapping("/removeDiscount/{productId}")
    public ResponseEntity<ProductDto> unApplyProduct(@PathVariable Integer productId) {
        ProductDto updatedProduct = this.productService.removeDiscount(productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable Integer categoryId) {
        List<ProductDto> productDtos = this.productService.getProductsByCategory(categoryId);
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

    @GetMapping("/totalProduct")
    public ResponseEntity<TotalProductDto> getTotalProduct() {
        TotalProductDto productDto = this.productService.getTotalProductInStore();
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }

    @GetMapping("/discounted")
    public ResponseEntity<List<ProductDto>> getProductsDiscounted() {
        List<ProductDto> productDtos = this.productService.getProductsHaveDiscount();
        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }
}
