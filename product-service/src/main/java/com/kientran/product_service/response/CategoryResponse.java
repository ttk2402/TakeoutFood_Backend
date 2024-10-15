package com.kientran.product_service.response;

import com.kientran.product_service.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CategoryResponse {
    private Integer id;
    private String title;
    private String url_image_category;
    private List<Product> products;
}