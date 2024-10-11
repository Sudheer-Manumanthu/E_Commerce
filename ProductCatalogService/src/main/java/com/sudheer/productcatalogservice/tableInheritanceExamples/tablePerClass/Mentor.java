package com.sudheer.productcatalogservice.tableInheritanceExamples.tablePerClass;


import jakarta.persistence.Entity;

@Entity(name = "tpc_Mentor")
public class Mentor extends User {
    String company;
}
