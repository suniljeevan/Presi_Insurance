package com.insurance.ClaimsManagement.service;

import com.insurance.ClaimsManagement.ResponseTemplateVo.Policy;
import com.insurance.ClaimsManagement.ResponseTemplateVo.ResponseTemplate;
import com.insurance.ClaimsManagement.entity.Claims;
import com.insurance.ClaimsManagement.repository.ClaimsRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
//When a class is annotated with @Service, it is automatically registered with Spring's application context as a bean, which means it can be injected into other components using dependency injection
@Slf4j
//It's a shorthand for declaring a logger instance for the class, which can then be used to log messages and other information.
public class ClaimsService
{
    @Autowired
    //When @Autowired is applied to a field, Spring will automatically inject the dependency at runtime.
    private ClaimsRepository claimsRepository;

    @Autowired
    //When @Autowired is applied to a field, Spring will automatically inject the dependency at runtime.
    private RestTemplate restTemplate;
    private static final String claimsManagementService = "Claims Service";

    //service methode to save policies
    public Claims saveClaims(Claims claims)
    {
        log.info("Inside saveDepartment of ClaimsService");
        return claimsRepository.save(claims);
    }


    //service methode to get policies
    public Claims findClaimsById(long claimsId)
    {
        log.info("Inside findClaimsById of ClaimsService");
        return claimsRepository.findByClaimsId(claimsId);
    }

    //service methode to get all policies
    public List<Claims> GetAllClaims()
    {
        log.info("Inside GetAllClaims of ClaimsService");
        return claimsRepository.findAll();
    }

    //service methode to update the claims by using claimsId
    public Claims updateClaims(Claims claims,long claimsId)
    {
        log.info("Inside updateClaims of ClaimsService");
        Claims getClaims = claimsRepository.findByClaimsId(claimsId);

        getClaims.setCustomerName(claims.getCustomerName());
        getClaims.setSex(claims.getSex());
        getClaims.setContactNo(claims.getContactNo());
        getClaims.setLossDate(claims.getLossDate());
        getClaims.setLossDescription(claims.getLossDescription());
        getClaims.setClaimStatus(claims.getClaimStatus());
        getClaims.setAmountSanctioned(claims.getAmountSanctioned());
        getClaims.setPoliceReport(claims.getPoliceReport());
        getClaims.setHasEvidence(claims.getHasEvidence());
        getClaims.setPolicyId(claims.getPolicyId());


        return claimsRepository.save(getClaims);
    }

    //service methode to delete the claims
    public Claims deleteById(long claimsId)
    {
        log.info("Inside deleteById of ClaimsService");
        claimsRepository.deleteById(claimsId);
        return null;
    }


    @CircuitBreaker(name = "claimsManagementService",fallbackMethod = "claimsFallback")
    @Retry(name= "claimsManagementService",fallbackMethod = "claimsRetryFallback")
    @RateLimiter(name = "claimsManagementService",fallbackMethod = "claimsRatelimiterFallback")
    public ResponseTemplate getClaimsWithPolicy(long claimsId)
    {
        log.info("Inside getClaimsWithPolicy of ClaimsService");
        ResponseTemplate vo = new ResponseTemplate();
        Claims claims = claimsRepository.findByClaimsId(claimsId);

        Policy policy =
                restTemplate.getForObject("http://POLICY-MANAGEMENT-SERVICE/policy/"+claims.getPolicyId()
                        ,Policy.class);

        vo.setClaims(claims);
        vo.setPolicy(policy);

        return vo;
    }


    //creating fall back methode for circuit breaker
    public ResponseEntity<String> claimsFallback(Exception e)
    {
        log.info("Inside ResponseEntity<Claims> claimsFallback of ClaimsService for circuit breaker");
        return new ResponseEntity<String>("The Service is down.", HttpStatus.OK);
    }

    //creating retry fall back methode for circuit breaker
    public ResponseEntity<String> claimsRetryFallback(Exception e)
    {
        log.info("Inside ResponseEntity<Claims> claimsRetryFallback of ClaimsService for circuit breaker");
        return new ResponseEntity<String>("The Service is down.", HttpStatus.OK);
    }

    public ResponseEntity<String> claimsRatelimiterFallback(Exception e)
    {
        log.info("Inside ResponseEntity<Claims> claimsRatelimiterFallback of ClaimsService for circuit breaker");
        return new ResponseEntity<String>("The Service is down.", HttpStatus.OK);
    }
}
