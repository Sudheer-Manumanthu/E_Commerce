package com.sudheer.userauthenticationservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String username;
    private String password;

    @ManyToMany
//    @JoinTable(
//            name = "user_roles", // Name of the join table
//            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),  // "id" column in User table
//            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") // "id" column in Role table
//    )
    private Set<Role> roles = new HashSet<>();
}
