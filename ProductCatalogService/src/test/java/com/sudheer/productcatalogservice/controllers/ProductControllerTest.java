package com.sudheer.productcatalogservice.controllers;

import com.sudheer.productcatalogservice.dtos.ProductDto;
import com.sudheer.productcatalogservice.models.Product;
import com.sudheer.productcatalogservice.services.IProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    IProductService productService;

    @Captor
    ArgumentCaptor<Long> idCaptor;

    @Test
    public void Test_getProductByIdThrowIllegelArgumentExpection(){

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {productController.getProductById(0L);});
        String expectedMessage = "Product id cannot be 0";
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    public void Test_getProductByIdSuccessfullyRun(){
        Product product = new Product();
        product.setId(1L);
        product.setName("test");
        product.setDescription("description");

        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("test");
        productDto.setDescription("description");

        when(productService.getProductById(1L)).thenReturn(product);

        assertNotNull(productController.getProductById(1L).getBody().getDescription());
        assertEquals(productDto.getName(), productController.getProductById(1L).getBody().getName());
        verify(productService, times(2)).getProductById(1L);
    }

    @Test
    public void Test_GetProductById_ServiceCalledWithExpectedArguments_RunSuccessfully() {
        //Arrange
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productService.getProductById(any(Long.class))).thenReturn(product);

        //Act
        productController.getProductById(productId);

        //Assert
        verify(productService).getProductById(idCaptor.capture());
        assertEquals(productId, idCaptor.getValue());
    }

}