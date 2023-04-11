package com.insurance.BillingManagement.service;

import com.insurance.BillingManagement.ResponceTemplate.Claims;
import com.insurance.BillingManagement.ResponceTemplate.Policy;
import com.insurance.BillingManagement.ResponceTemplate.Response;
import com.insurance.BillingManagement.entity.Bills;
import com.insurance.BillingManagement.repository.BillsRepository;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
//When a class is annotated with @Service, it is automatically registered with Spring's application context as a bean, which means it can be injected into other components using dependency injection
@Slf4j
//It's a shorthand for declaring a logger instance for the class, which can then be used to log messages and other information.
public class BillsService
{
    @Autowired
    //When @Autowired is applied to a field, Spring will automatically inject the dependency at runtime.
    private BillsRepository billsRepository;

    @Autowired
    //When @Autowired is applied to a field, Spring will automatically inject the dependency at runtime.
    private RestTemplate restTemplate;
    private static final String billsManagementService = "Bills Service";

    //service methode to save policies
    public Bills saveBills(Bills bills)
    {
        log.info("Inside saveDepartment of BillsService");
        return billsRepository.save(bills);
    }


    //service methode to get policies
    public Bills findBillsById(long billsId)
    {
        log.info("Inside findBillsById of BillsService");
        return billsRepository.findByBillsId(billsId);
    }

    //service methode to get all policies
    public List<Bills> GetAllBills()
    {
        log.info("Inside GetAllBills of BillsService");
        return billsRepository.findAll();
    }

    //service methode to update the claims by using claimsId
    public Bills updateBills(Bills bills,long billsId)
    {
        log.info("Inside updateBills of BillsService");
        Bills getBils = billsRepository.findByBillsId(billsId);

        getBils.setClaimDate(bills.getClaimDate());
        getBils.setClaimType(bills.getClaimType());
        getBils.setClaimStatus(bills.getClaimStatus());
        getBils.setPayoutAmount(bills.getPayoutAmount());
        getBils.setPayoutDate(bills.getPayoutDate());
        getBils.setClaimDenialDetails(bills.getClaimDenialDetails());
        getBils.setPaymentMethode(bills.getPaymentMethode());
        getBils.setClaimsId(bills.getClaimsId());
        getBils.setPolicyId(bills.getPolicyId());

        return billsRepository.save(getBils);
    }

    //service methode to delete the billd
    public Bills deleteById(long billsId)
    {
        log.info("Inside deleteById of BillsService");
        billsRepository.deleteById(billsId);
        return null;
    }


    @CircuitBreaker(name = "billsManagementService",fallbackMethod = "billsFallback")
    @Retry(name= "billsManagementService",fallbackMethod = "billsRetryFallback")
    @RateLimiter(name = "billsManagementService",fallbackMethod = "billsRatelimiterFallback")
    public Response getClaimsAndPolicyWithBills(long billsId)
    {
        Response vo = new Response();
        Bills bills = billsRepository.findByBillsId(billsId);

        Policy policy =
                restTemplate.getForObject("http://POLICY-MANAGEMENT-SERVICE/policy/"+bills.getPolicyId()
                        ,Policy.class);

        Claims claims =
                restTemplate.getForObject("http://CLAIMS-MANAGEMENT-SERVICE/claims/"+bills.getClaimsId()
                        ,Claims.class);

        vo.setBills(bills);
        vo.setClaims(claims);
        vo.setPolicy(policy);

        return vo;
    }


    //creating fall back methode for circuit breaker
    public ResponseEntity<String> billsFallback(Exception e)
    {
        log.info("Inside ResponseEntity<Claims> billsFallback of BillsService for circuit breaker");
        return new ResponseEntity<String>("The Service is down.", HttpStatus.OK);
    }

    //creating retry fall back methode for circuit breaker
    public ResponseEntity<String> billsRetryFallback(Exception e)
    {
        log.info("Inside ResponseEntity<Claims> billsRetryFallback of ClaimsService for circuit breaker");
        return new ResponseEntity<String>("The Service is down.", HttpStatus.OK);
    }

    public ResponseEntity<String> billsRatelimiterFallback(Exception e)
    {
        log.info("Inside ResponseEntity<Claims> claimsRatelimiterFallback of ClaimsService for circuit breaker");
        return new ResponseEntity<String>("The Service is down.", HttpStatus.OK);
    }
}