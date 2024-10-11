package com.sudheer.userauthenticationservice.controllers;


import com.sudheer.userauthenticationservice.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler({RuntimeException.class, UserAlreadyExistsException.class, InvalidCredentialsException.class, SessionDoesNotExistsException.class, UserDoesNotExistsException.class, RoleDoesNotExists.class})
    public ResponseEntity<String> controllerAdvisor(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
