package com.sudheer.productcatalogservice.services;

import com.sudheer.productcatalogservice.exceptions.ProductDoesNotExists;
import com.sudheer.productcatalogservice.exceptions.UserDoesNotExists;
import com.sudheer.productcatalogservice.exceptions.UserNotAnAdmin;
import com.sudheer.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {
    public List<Product> getAllProducts();
    public Product getProductById(Long id);
    public Product createProduct(Product product);
    public Product replaceProduct(Product product, Long id);

    Product getProductByUserScope(Long productid, Long userid) throws ProductDoesNotExists, UserDoesNotExists, UserNotAnAdmin;
}
