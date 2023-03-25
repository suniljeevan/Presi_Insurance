package com.insurance.policymanagment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Policy
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long policyId;
    private String policyName;
    private String policyCoverageTime;
    private long premiumAmount;
    private String policyCoverageAmount;
}
