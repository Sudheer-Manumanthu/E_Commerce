package com.sudheer.productcatalogservice.exceptions;

public class UserNotAnAdmin extends Exception{
    public UserNotAnAdmin(String message) {
        super(message);
    }
}
