package com.insurance.ClaimsManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
//When @Entity is applied to a Java class, it instructs the JPA provider to create a table in the database that corresponds to the class
@AllArgsConstructor
//@AllArgsConstructor is a Lombok annotation that automatically generates a constructor with arguments for all non-final fields in a class
@NoArgsConstructor
//This can be useful when working with frameworks or libraries that require a default constructor, or when creating classes for data transfer objects (DTOs) or value objects.
public class Claims
{
    @Id
    //In a relational database, a primary key is a unique identifier that is used to identify each record in a table.
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long claimsId;
    private String customerName;
    private String sex;
    private String contactNo;
    private String lossDate;
    private String lossDescription;
    private String claimStatus;
    private long amountSanctioned;
    private String policeReport;
    private String hasEvidence;
    private long policyId;
}