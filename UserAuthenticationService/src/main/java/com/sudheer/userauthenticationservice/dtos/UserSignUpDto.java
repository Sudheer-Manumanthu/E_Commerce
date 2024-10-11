package com.sudheer.userauthenticationservice.dtos;


import com.sudheer.userauthenticationservice.models.Role;
import lombok.Getter;
import lombok.Setter;


import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserSignUpDto {
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<>();
}
