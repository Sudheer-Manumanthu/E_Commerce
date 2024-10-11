package com.sudheer.productcatalogservice.tableInheritanceExamples.joinedClass;


import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity(name = "jc_Ta")
@PrimaryKeyJoinColumn(name = "User_Id")
public class Ta extends User {
    int noOfHours;
}
