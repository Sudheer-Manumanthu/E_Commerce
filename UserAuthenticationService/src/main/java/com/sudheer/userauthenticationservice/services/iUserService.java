package com.sudheer.userauthenticationservice.services;

import com.sudheer.userauthenticationservice.exceptions.UserDoesNotExistsException;
import com.sudheer.userauthenticationservice.models.User;

public interface iUserService {
    public User getUserById(Long id) throws UserDoesNotExistsException;
}
