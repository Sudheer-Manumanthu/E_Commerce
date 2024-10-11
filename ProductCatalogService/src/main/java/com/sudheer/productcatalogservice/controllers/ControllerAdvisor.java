package com.sudheer.productcatalogservice.controllers;


import com.sudheer.productcatalogservice.exceptions.ProductDoesNotExists;
import com.sudheer.productcatalogservice.exceptions.UserDoesNotExists;
import com.sudheer.productcatalogservice.exceptions.UserNotAnAdmin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class, ProductDoesNotExists.class, UserDoesNotExists.class, UserNotAnAdmin.class})
    public ResponseEntity<String> handleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
