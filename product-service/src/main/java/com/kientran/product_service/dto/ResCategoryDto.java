package com.kientran.product_service.dto;

import com.kientran.product_service.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResCategoryDto {
    private Integer id;
    private String title;
    private String url_image_category;
    private List<Product> products;
}