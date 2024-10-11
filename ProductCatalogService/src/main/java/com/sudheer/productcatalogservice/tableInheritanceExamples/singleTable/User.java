package com.sudheer.productcatalogservice.tableInheritanceExamples.singleTable;


import jakarta.persistence.*;

@Entity(name = "st_User")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "UserType", discriminatorType = DiscriminatorType.INTEGER)
public class User {
    @Id
    Long id;
    String name;
}
