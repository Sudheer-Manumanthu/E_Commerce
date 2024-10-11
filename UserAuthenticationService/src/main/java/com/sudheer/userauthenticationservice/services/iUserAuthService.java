package com.sudheer.userauthenticationservice.services;

import com.sudheer.userauthenticationservice.exceptions.*;
import com.sudheer.userauthenticationservice.exceptions.UserAlreadyExistsException;
import com.sudheer.userauthenticationservice.models.Role;
import com.sudheer.userauthenticationservice.models.User;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.util.MultiValueMap;

import java.util.Set;

public interface iUserAuthService {
    public Pair<User, MultiValueMap<String, String>> login(String username, String password) throws InvalidCredentialsException;
    public User signUp(String username, String password,Set<Role> roleSet) throws UserAlreadyExistsException, RoleDoesNotExists;
    public User logOut(String username, String password);
    public Boolean validateToken(String token, Long userId) throws SessionDoesNotExistsException;
}
