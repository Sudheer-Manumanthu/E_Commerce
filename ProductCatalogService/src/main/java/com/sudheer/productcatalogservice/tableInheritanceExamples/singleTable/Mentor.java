package com.sudheer.productcatalogservice.tableInheritanceExamples.singleTable;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "1")
public class Mentor extends User {
    String company;
}
