package com.insurance.ClaimsManagement.repository;

import com.insurance.ClaimsManagement.entity.Claims;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaimsRepository extends JpaRepository<Claims, Long>
{
    Claims findByClaimsId(long claimsId);

}