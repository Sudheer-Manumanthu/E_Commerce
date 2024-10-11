package com.sudheer.productcatalogservice.controllers;


import com.sudheer.productcatalogservice.dtos.CategoryDto;
import com.sudheer.productcatalogservice.dtos.FakeStoreProductDto;
import com.sudheer.productcatalogservice.dtos.ProductDto;
import com.sudheer.productcatalogservice.exceptions.ProductDoesNotExists;
import com.sudheer.productcatalogservice.exceptions.UserDoesNotExists;
import com.sudheer.productcatalogservice.exceptions.UserNotAnAdmin;
import com.sudheer.productcatalogservice.models.Category;
import com.sudheer.productcatalogservice.models.Product;
import com.sudheer.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    IProductService IProductService;

    @GetMapping()
    public List<ProductDto> getProducts() {
        List<Product> products = IProductService.getAllProducts();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(getProductDto(product));
        }
        return productDtos;
    }

    @GetMapping("/{productid}/{userid}")
    public ResponseEntity<ProductDto> getProductByUserScope(@PathVariable("productid") Long productid, @PathVariable("userid") Long userid) throws UserDoesNotExists, ProductDoesNotExists, UserNotAnAdmin {
        if (productid == 0) {
            throw new IllegalArgumentException("Product id cannot be 0");
        }
        ProductDto productDto = getProductDto(IProductService.getProductByUserScope(productid, userid));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("CreatedBy", "Sudheer");
        return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        if (productId == 0) {
            throw new IllegalArgumentException("Product id cannot be 0");
        }
        ProductDto productDto = getProductDto(IProductService.getProductById(productId));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("CreatedBy", "Sudheer");
        return new ResponseEntity<>(productDto, headers, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product) {
        Product input = getProduct(product);
        Product response = IProductService.createProduct(input);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("CreatedBy", "Sudheer");
        return new ResponseEntity<>(getProductDto(response), headers, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDto> replaceProduct(@RequestBody ProductDto product, @PathVariable Long id) {

        if (id == 0) {
            throw new IllegalArgumentException("Product id cannot be 0");
        }
        Product input = getProduct(product);
        Product response = IProductService.replaceProduct(input, id);
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("CreatedBy", "Sudheer");
        return new ResponseEntity<>(getProductDto(response), headers, HttpStatus.OK);
    }

    private Product getProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        if(productDto.getCategory() != null) {
            Category category = new Category();
            category.setId(productDto.getCategory().getId());
            category.setName(productDto.getCategory().getName());
            product.setCategory(category);
        }
        return product;
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
