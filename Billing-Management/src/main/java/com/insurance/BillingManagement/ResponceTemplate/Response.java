package com.insurance.BillingManagement.ResponceTemplate;


import com.insurance.BillingManagement.entity.Bills;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response
{
    private Policy policy;
    private Claims claims;
    private Bills bills;
}
