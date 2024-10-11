package com.sudheer.productcatalogservice.exceptions;

public class ProductDoesNotExists extends Exception{
    public ProductDoesNotExists(String message) {
        super(message);
    }
}
