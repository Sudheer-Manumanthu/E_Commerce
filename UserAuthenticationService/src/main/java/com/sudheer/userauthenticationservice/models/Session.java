package com.sudheer.userauthenticationservice.models;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Entity
public class Session extends BaseModel{
    @ManyToOne
    private User user;
    private String token;
}
