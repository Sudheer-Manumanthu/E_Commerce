package com.sudheer.productcatalogservice.dtos;

import com.sudheer.productcatalogservice.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    public List<Product> products;
}
