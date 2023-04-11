package com.insurance.PolicyManagement.service;

import com.insurance.PolicyManagement.entity.Policy;
import com.insurance.PolicyManagement.repsitory.PolicyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
//When a class is annotated with @Service, it is automatically registered with Spring's application context as a bean, which means it can be injected into other components using dependency injection
@Slf4j
//It's a shorthand for declaring a logger instance for the class, which can then be used to log messages and other information.
public class PolicyService
{
    @Autowired
    //When @Autowired is applied to a field, Spring will automatically inject the dependency at runtime.
    private PolicyRepository policyRepository;

    //service methode to save policies
    public Policy savePolicies(Policy policy)
    {
        log.info("Inside saveDepartment of PolicyService");
        return policyRepository.save(policy);
    }

    //service methode to get all policies
    public List<Policy> GetAllPolicies()
    {
        log.info("Inside GetAllPolicies of PolicyService");
        return policyRepository.findAll();
    }

    //service methode to get policies
    public Policy findPolicyById(long policyId)
    {
        log.info("Inside findPolicyById of PolicyService");
        return policyRepository.findByPolicyId(policyId);
    }

    //service methode to update the policy by policyId
    public Policy updatePolicy(Policy policy,long policyId)
    {
        log.info("Inside updatePolicy of PolicyService");

        Policy getPolicy =policyRepository.findByPolicyId(policyId);

        getPolicy.setPolicyName(policy.getPolicyName());
        getPolicy.setPolicyType(policy.getPolicyType());
        getPolicy.setPolicyOwner(policy.getPolicyOwner());
        getPolicy.setPolicyDescription(policy.getPolicyDescription());
        getPolicy.setPolicyStatus(policy.getPolicyStatus());
        getPolicy.setPolicyCoverageTime(policy.getPolicyCoverageTime());
        getPolicy.setPremiumAmount(policy.getPremiumAmount());
        getPolicy.setPolicyCoverageTime(policy.getPolicyCoverageTime());

        return policyRepository.save(getPolicy);
    }

    //service methode to delete the policy by policyId
    public Policy deleteById(long policyId)
    {
        log.info("Inside deleteById of PolicyService");
        policyRepository.deleteById(policyId);
        return null;
    }

}
