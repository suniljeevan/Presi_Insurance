package com.insurance.BillingManagement.ResponceTemplate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Claims
{
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
