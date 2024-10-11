package com.sudheer.productcatalogservice.tableInheritanceExamples.joinedClass;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "jc_Mentor")
@PrimaryKeyJoinColumn(name = "User_Id")
public class Mentor extends User {
    String company;
}
