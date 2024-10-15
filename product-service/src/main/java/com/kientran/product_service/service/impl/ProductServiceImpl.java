package com.kientran.product_service.service.impl;

import com.kientran.product_service.dto.ProductDto;
import com.kientran.product_service.dto.TotalProductDto;
import com.kientran.product_service.entity.Category;
import com.kientran.product_service.entity.Discount;
import com.kientran.product_service.entity.Product;
import com.kientran.product_service.exception.ResourceNotFoundException;
import com.kientran.product_service.repository.CategoryRepository;
import com.kientran.product_service.repository.DiscountRepository;
import com.kientran.product_service.repository.ProductRepository;
import com.kientran.product_service.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepo, CategoryRepository categoryRepo, DiscountRepository discountRepository, ModelMapper modelMapper) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.discountRepository = discountRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category ", "categoryId", categoryId));
        Product product = this.modelMapper.map(productDto, Product.class);
        product.setCategory(category);
        Product newProduct = this.productRepo.save(product);
        return this.modelMapper.map(newProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, Integer productId) {
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product ", "ProductId", productId));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setUrl_image_product(productDto.getUrl_image_product());
        Product updateProduct = this.productRepo.save(product);
        return this.modelMapper.map(updateProduct, ProductDto.class);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
        this.productRepo.delete(product);
    }

    @Override
    public ProductDto getProductById(Integer productId) {
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
        return this.modelMapper.map(product, ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = this.productRepo.findAll();
        return products.stream().map((product) -> this.modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto applyDiscount(Integer productId, Integer discountId) {
        Discount discount = this.discountRepository.findById(discountId)
                .orElseThrow(() -> new ResourceNotFoundException("Discount", "discountId", discountId));
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
        product.setDiscount(discount);
        double price = product.getPrice();
        product.setPrice(price-price*discount.getPercent());
        Product updateProduct = this.productRepo.save(product);
        return this.modelMapper.map(updateProduct, ProductDto.class);
    }

    @Override
    public ProductDto removeDiscount(Integer productId) {
        Product product = this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));
        Discount discount = product.getDiscount();
        double currentPrice = product.getPrice();
        double originalPrice = currentPrice / (1 - discount.getPercent());
        product.setPrice(originalPrice);
        product.setDiscount(null);
        Product updateProduct = this.productRepo.save(product);
        return this.modelMapper.map(updateProduct, ProductDto.class);
    }

    @Override
    public List<ProductDto> getProductsByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category ", "categoryId", categoryId));
        List<Product> products = this.productRepo.findByCategory(category);
        return products.stream().map((product) -> this.modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TotalProductDto getTotalProductInStore() {
        TotalProductDto productDto = new TotalProductDto();
        productDto.setTotal(this.productRepo.getTotalProduct());
        return productDto;
    }

    @Override
    public List<ProductDto> getProductsHaveDiscount() {
        List<Product> products = this.productRepo.findAll();
        List<Product> productsDiscount = new ArrayList<>();
        for(Product product : products) {
            if(product.getDiscount() != null) {
                productsDiscount.add(product);
            }
        }
        return productsDiscount.stream().map((product) -> this.modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }
}
