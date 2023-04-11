package com.insurance.PolicyManagement.entity;

import lombok.*;
import org.springframework.context.annotation.Bean;

import javax.persistence.*;


@Data
@Entity
//When @Entity is applied to a Java class, it instructs the JPA provider to create a table in the database that corresponds to the class
@AllArgsConstructor
//@AllArgsConstructor is a Lombok annotation that automatically generates a constructor with arguments for all non-final fields in a class
@NoArgsConstructor
//This can be useful when working with frameworks or libraries that require a default constructor, or when creating classes for data transfer objects (DTOs) or value objects.
public class Policy
{
    @Id
    //In a relational database, a primary key is a unique identifier that is used to identify each record in a table.
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long policyId;
    private String policyName;
    private String policyType;
    private String policyOwner;
    private String policyDescription;
    private String policyStatus;
    private String policyCoverageTime;
    private String premiumAmount;
    private String policyCoverageAmount;
}
