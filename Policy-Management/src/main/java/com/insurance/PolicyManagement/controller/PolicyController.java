package com.insurance.PolicyManagement.controller;

import com.insurance.PolicyManagement.entity.Policy;
import com.insurance.PolicyManagement.service.PolicyService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
//@RestController is a Spring Framework annotation used to simplify the creation of RESTful web services
@RequestMapping("/policy")
//@RequestMapping is a Spring Framework annotation used to map HTTP requests to specific methods in a controller
@Slf4j
//It's a shorthand for declaring a logger instance for the class, which can then be used to log messages and other information.
public class PolicyController
{
    @Autowired
    //When @Autowired is applied to a field, Spring will automatically inject the dependency at runtime.
    private PolicyService policyService;

    //methode to create a policy
    //@RequestBody is a Spring Framework annotation used to bind the HTTP request body to a Java object in a RESTful API endpoint
    @PostMapping("/")
    @CircuitBreaker(name = "policyManagementService",fallbackMethod = "policyFallback")
    @Retry(name= "policyManagementService",fallbackMethod = "policyRetryFallback")
    @RateLimiter(name = "policyManagementService",fallbackMethod = "policyRatelimiterFallback")
    //@PostMapping is an annotation used in Java Spring to map HTTP POST requests to specific controller methods
    public Policy savePolicies(@RequestBody Policy policy)
    {
        //entering to the log
        log.info("Inside savePolicies methode of the PolicyController.");
        return policyService.savePolicies(policy);
    }

    //methode to update a policy
    //@RequestBody is a Spring Framework annotation used to bind the HTTP request body to a Java object in a RESTful API endpoint
    @PutMapping("/{id}")
    @CircuitBreaker(name = "policyManagementService",fallbackMethod = "policyFallback")
    @Retry(name= "policyManagementService",fallbackMethod = "policyRetryFallback")
    @RateLimiter(name = "policyManagementService",fallbackMethod = "policyRatelimiterFallback")
    //@PostMapping is an annotation used in Java Spring to map HTTP POST requests to specific controller methods
    public Policy updatePolicies(@RequestBody Policy policy,@PathVariable("id") long policyId)
    {
        //entering to the log
        log.info("Inside updatePolicies methode of the PolicyController.");
        return policyService.updatePolicy(policy,policyId);
    }

    //methode to get the policy by id number
    @GetMapping("/{id}")
    @CircuitBreaker(name = "policyManagementService",fallbackMethod = "policyFallback")
    @Retry(name= "policyManagementService",fallbackMethod = "policyRetryFallback")
    @RateLimiter(name = "policyManagementService",fallbackMethod = "policyRatelimiterFallback")
    //@GetMapping is an annotation used in Spring Framework's MVC module to handle HTTP GET requests
    public Policy findPolicyById(@PathVariable("id") long policyId)
    {
        //entering to the log
        log.info("Inside findPolicyById methode of the PolicyController.");
        return policyService.findPolicyById(policyId);
    }

    @GetMapping()
    @CircuitBreaker(name = "policyManagementService",fallbackMethod = "policyFallback")
    @Retry(name= "policyManagementService",fallbackMethod = "policyRetryFallback")
    @RateLimiter(name = "policyManagementService",fallbackMethod = "policyRatelimiterFallback")
    //@GetMapping is an annotation used in Spring Framework's MVC module to handle HTTP GET requests
    public List<Policy> GetAllPolicies()
    {
        //entering to the log
        log.info("Inside GetAllPolicies methode of the PolicyController.");
         return policyService.GetAllPolicies();
    }

    //methode to delete a policy
    //@RequestBody is a Spring Framework annotation used to bind the HTTP request body to a Java object in a RESTful API endpoint
    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "policyManagementService",fallbackMethod = "policyFallback")
    @Retry(name= "policyManagementService",fallbackMethod = "policyRetryFallback")
    @RateLimiter(name = "policyManagementService",fallbackMethod = "policyRatelimiterFallback")
    //@PostMapping is an annotation used in Java Spring to map HTTP POST requests to specific controller methods
    public Policy deletePolicies(@PathVariable("id") long policyId)
    {
        //entering to the log
        log.info("Inside deletePolicies methode of the PolicyController.");
        return policyService.deleteById(policyId);
    }

    //creating fall back methode for circuit breaker
    public ResponseEntity<String> policyFallback(Exception e) {
        log.info("Inside ResponseEntity<Claims> policyFallback of ClaimsService for circuit breaker");
        return new ResponseEntity<String>("The Service is down.", HttpStatus.OK);
    }

    //creating retry fall back methode for circuit breaker
    public ResponseEntity<String> policyRetryFallback(Exception e)
    {
        log.info("Inside ResponseEntity<Claims> policyRetryFallback of ClaimsService for circuit breaker");
        return new ResponseEntity<String>("The Service is down.", HttpStatus.OK);
    }

    public ResponseEntity<String> policyRatelimiterFallback(Exception e)
    {
        log.info("Inside ResponseEntity<Claims> policyRatelimiterFallback of ClaimsService for circuit breaker");
        return new ResponseEntity<String>("The Service is down.", HttpStatus.OK);
    }


}
