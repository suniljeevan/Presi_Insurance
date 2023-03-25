package com.insurance.policymanagment.service;

import com.insurance.policymanagment.entity.Policy;
import com.insurance.policymanagment.repository.PolicyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PolicyService
{
    @Autowired
    private PolicyRepository policyRepository;


    public Policy savePolicies(Policy policy)
    {
        log.info("Inside saveDepartment of PolicyService");
        return policyRepository.save(policy);
    }

    public Policy findPolicyById(long policyId)
    {
        log.info("Inside findPolicyById of PolicyService");
        return policyRepository.findByPolicyId(policyId);
    }
}
