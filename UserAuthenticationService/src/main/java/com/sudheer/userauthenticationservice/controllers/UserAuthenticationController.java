package com.sudheer.userauthenticationservice.controllers;


import com.sudheer.userauthenticationservice.dtos.UserDto;
import com.sudheer.userauthenticationservice.dtos.UserLoginDto;
import com.sudheer.userauthenticationservice.dtos.UserSignUpDto;
import com.sudheer.userauthenticationservice.dtos.ValidateTokenDto;
import com.sudheer.userauthenticationservice.exceptions.*;
import com.sudheer.userauthenticationservice.models.Role;
import com.sudheer.userauthenticationservice.models.User;
import com.sudheer.userauthenticationservice.services.UserAuthService;
import com.sudheer.userauthenticationservice.services.iUserAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class UserAuthenticationController {

    @Autowired
    iUserAuthService userAuthService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> UserSignUp(@RequestBody UserSignUpDto userSignUpDto) throws IllegalArgumentsException, UserAlreadyExistsException, RoleDoesNotExists {
        if (userSignUpDto.getUsername() == null || userSignUpDto.getPassword() == null) {
            throw new IllegalArgumentsException("Illegal arguments");
            //return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        User user =  userAuthService.signUp(userSignUpDto.getUsername(), userSignUpDto.getPassword(), userSignUpDto.getRoles());
        UserDto userDto = mapper(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> UserLogin(@RequestBody UserLoginDto userLoginDto) throws IllegalArgumentsException, UserAlreadyExistsException, UserDoesNotExistsException, InvalidCredentialsException {
        if (userLoginDto.getUsername() == null || userLoginDto.getPassword() == null) {
            throw new IllegalArgumentException("Illegal arguments");
        }

        Pair<User, MultiValueMap<String, String>> pair = userAuthService.login(userLoginDto.getUsername(), userLoginDto.getPassword());

        if (pair == null) {
            throw new UserDoesNotExistsException("User " + userLoginDto.getUsername() + " does not exist");
        }


        UserDto userDto = mapper(pair.a);
        return new ResponseEntity<>(userDto, pair.b,HttpStatus.OK);
    }

    @PostMapping("/validateToken")
    public Boolean validateToken(@RequestBody ValidateTokenDto validateTokenDto) throws SessionDoesNotExistsException {
        Boolean result = userAuthService.validateToken(validateTokenDto.getToken(), validateTokenDto.getUserId());
        return result;
    }

    @PostMapping("/logout")
    public ResponseEntity<UserDto> UserLogout(){
        return null;
    }

    private UserDto mapper(User user){
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

}
