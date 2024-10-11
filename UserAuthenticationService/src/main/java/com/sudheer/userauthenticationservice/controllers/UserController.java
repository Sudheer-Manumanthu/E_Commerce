package com.sudheer.userauthenticationservice.controllers;

import com.sudheer.userauthenticationservice.dtos.UserDto;
import com.sudheer.userauthenticationservice.exceptions.UserDoesNotExistsException;
import com.sudheer.userauthenticationservice.models.User;
import com.sudheer.userauthenticationservice.services.iUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    iUserService userService;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable("id") Long id) throws UserDoesNotExistsException {
        User user = userService.getUserById(id);
        UserDto userDto = mapper(user);
        return userDto;
    }

    private UserDto mapper(User user){
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

}
