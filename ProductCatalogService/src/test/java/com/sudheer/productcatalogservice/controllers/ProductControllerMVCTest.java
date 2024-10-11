package com.sudheer.productcatalogservice.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sudheer.productcatalogservice.dtos.ProductDto;
import com.sudheer.productcatalogservice.models.Product;
import com.sudheer.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
class ProductControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductService productService;


    //object <-> json <-> string
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void Test_GetAllProductsAPI_TestsStatusOnly() throws Exception {

        mockMvc.perform(get("/product")).andExpect(status().isOk());
    }

    @Test
    public void Test_CreateProduct_CreatesSuccessfully() throws Exception {
        Product product = new Product();
        product.setName("Test");
        product.setDescription("Test");

        ProductDto productDto = new ProductDto();
        productDto.setName("Test");
        productDto.setDescription("Test");

        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/product").content(objectMapper.writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDto)));

    }

    @Test
    public void Test_GetAllProductsAPI_TestResponseAndStatus() throws Exception {
        Product product1 = new Product();
        product1.setId(24L);
        product1.setName("Product 1");
        Product product2 = new Product();
        product2.setId(25L);
        product2.setName("Product 2");
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        ProductDto productDto = new ProductDto();
        productDto.setId(24L);
        productDto.setName("Product 1");
        ProductDto productDto2 = new ProductDto();
        productDto2.setId(25L);
        productDto2.setName("Product 2");

        List<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(productDto);
        productDtos.add(productDto2);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/product"))
                .andExpect(status().is2xxSuccessful())
                        .andExpect(content().string(objectMapper.writeValueAsString(productDtos)));

    }

    @Test
    @DisplayName("Asserting with Json")
    public void Test_GetAllProducts_AssertonJason() throws Exception {

//        Product product1 = new Product();
//        product1.setName("Iphone12");
//        Product product2 = new Product();
//        product2.setName("Iphone15");
//        List<Product> products = new ArrayList<>();
//        products.add(product1);
//        products.add(product2);
//
//        when(productService.getAllProducts()).thenReturn(products);
//
//        mockMvc.perform(get("/product"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(objectMapper.writeValueAsString(products)));


        Product product1 = new Product();
        product1.setId(24L);
        product1.setName("Product 1");
        Product product2 = new Product();
        product2.setId(25L);
        product2.setName("Product 2");
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        ProductDto productDto = new ProductDto();
        productDto.setId(24L);
        productDto.setName("Product 1");
        ProductDto productDto2 = new ProductDto();
        productDto2.setId(25L);
        productDto2.setName("Product 2");

        List<ProductDto> productDtos = new ArrayList<>();
        productDtos.add(productDto);
        productDtos.add(productDto2);

        when(productService.getAllProducts()).thenReturn(products);


        mockMvc.perform(get("/product"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].name").value("Product 2"));
    }

}