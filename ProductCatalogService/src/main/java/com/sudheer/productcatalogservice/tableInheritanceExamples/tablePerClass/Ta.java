package com.sudheer.productcatalogservice.tableInheritanceExamples.tablePerClass;


import jakarta.persistence.Entity;

@Entity(name = "tpc_Ta")
public class Ta extends User{
    int noOfHours;
}
