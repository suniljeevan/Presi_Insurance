package com.insurance.policymanagment.controller;

import com.insurance.policymanagment.entity.Policy;
import com.insurance.policymanagment.service.PolicyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/policies")
@Slf4j
public class PolicyController
{
    @Autowired
    private PolicyService policyService;

    @PostMapping("/")
    public Policy savePolicies(@RequestBody Policy policy)
    {
        log.info("Inside savePolicies methode of the PolicyController.");
        return policyService.savePolicies(policy);
    }

    @GetMapping("/{id}")
    public Policy findPolicyById(@PathVariable("id") long policyId)
    {
        log.info("Inside findPolicyById methode of the PolicyController.");
        return policyService.findPolicyById(policyId);
    }
}
