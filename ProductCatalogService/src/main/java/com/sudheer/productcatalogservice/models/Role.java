package com.sudheer.productcatalogservice.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  Role extends BaseModel {
    private String roleName;
}