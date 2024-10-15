package com.kientran.product_service.dto;

import com.kientran.product_service.entity.Category;
import com.kientran.product_service.entity.Discount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private Double price;
    private String description;
    private String url_image_product;
    private Category category;
    private Discount discount;
}

