package com.sudheer.productcatalogservice.dtos;


import com.sudheer.productcatalogservice.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String username;
    private Set<Role> roles;
}
