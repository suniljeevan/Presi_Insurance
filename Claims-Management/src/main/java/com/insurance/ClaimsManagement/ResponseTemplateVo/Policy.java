package com.insurance.ClaimsManagement.ResponseTemplateVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Policy
{
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
