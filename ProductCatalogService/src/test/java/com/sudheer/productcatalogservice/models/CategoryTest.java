package com.sudheer.productcatalogservice.models;

import com.sudheer.productcatalogservice.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @Transactional
    public void testCategory() {
        List<Category> categories = categoryRepository.findAll();
        for (Category category : categories) {
            List<Product> products = category.getProducts();
            if (!products.isEmpty()) {
                for (Product product : products) {
                    System.out.println(product.getName());
                }
            }
        }

    }



}