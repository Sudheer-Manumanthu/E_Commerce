package com.sudheer.productcatalogservice.dtos;

import com.sudheer.productcatalogservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Category category;
    private String imageUrl;
}
