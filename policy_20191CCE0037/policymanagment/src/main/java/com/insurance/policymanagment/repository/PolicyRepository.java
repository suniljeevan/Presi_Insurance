package com.insurance.policymanagment.repository;

import com.insurance.policymanagment.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long>
{

    Policy findByPolicyId(long policyId);
}
