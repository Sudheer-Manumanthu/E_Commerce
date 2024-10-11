package com.sudheer.productcatalogservice.controllers;


import com.sudheer.productcatalogservice.dtos.ProductDto;
import com.sudheer.productcatalogservice.dtos.SearchRequestDto;
import com.sudheer.productcatalogservice.dtos.SortParam;
import com.sudheer.productcatalogservice.models.Category;
import com.sudheer.productcatalogservice.models.Product;
import com.sudheer.productcatalogservice.services.IProductService;
import com.sudheer.productcatalogservice.services.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    @PostMapping
    public ResponseEntity<Page<ProductDto>> searchProduct(@RequestBody SearchRequestDto searchRequestDto) {

        System.out.println(searchRequestDto.getQuery() + " " + searchRequestDto.getPageNo() + " " + searchRequestDto.getPageSize());

        System.out.println(searchRequestDto.getSortParamList().size());

        System.out.println(searchRequestDto.getSortParamList());

//        for(SortParam sortParam : searchRequestDto.getSortParamList()) {
//            System.out.println(sortParam);
//        }
//
        for(SortParam sortParam : searchRequestDto.getSortParamList()) {
            System.out.println(sortParam.getParameter());
        }

        Page<Product> products = searchService.searchProducts(searchRequestDto.getQuery(), searchRequestDto.getPageNo(), searchRequestDto.getPageSize(), searchRequestDto.getSortParamList());



        Page<ProductDto> productDtoPage = products.map(this::getProductDto);

        return new ResponseEntity<Page<ProductDto>>(productDtoPage, HttpStatus.OK);
    }

    private ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setDescription(product.getDescription());
        if(product.getCategory() != null) {
            Category category = new Category();
            category.setId(product.getCategory().getId());
            category.setName(product.getCategory().getName());
            productDto.setCategory(category);
        }
        return productDto;
    }
}
