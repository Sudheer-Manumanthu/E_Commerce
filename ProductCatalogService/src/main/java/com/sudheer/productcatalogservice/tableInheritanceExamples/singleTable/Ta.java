package com.sudheer.productcatalogservice.tableInheritanceExamples.singleTable;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "2")
public class Ta extends User {
    int noOfHours;
}
