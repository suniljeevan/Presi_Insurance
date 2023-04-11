package com.insurance.ClaimsManagement.controller;

import com.insurance.ClaimsManagement.ResponseTemplateVo.ResponseTemplate;
import com.insurance.ClaimsManagement.entity.Claims;
import com.insurance.ClaimsManagement.service.ClaimsService;
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
@RequestMapping("/claims")
//@RequestMapping is a Spring Framework annotation used to map HTTP requests to specific methods in a controller
@Slf4j
//It's a shorthand for declaring a logger instance for the class, which can then be used to log messages and other information.
public class ClaimsController
{
    @Autowired
    //When @Autowired is applied to a field, Spring will automatically inject the dependency at runtime.
    private ClaimsService claimsService;

    //methode to create a claims
    //@RequestBody is a Spring Framework annotation used to bind the HTTP request body to a Java object in a RESTful API endpoint
    @PostMapping("/")
    @CircuitBreaker(name = "claimsManagementService",fallbackMethod = "claimsFallback")
    @Retry(name= "claimsManagementService",fallbackMethod = "claimsRetryFallback")
    @RateLimiter(name = "claimsManagementService",fallbackMethod = "claimsRatelimiterFallback")
    //@PostMapping is an annotation used in Java Spring to map HTTP POST requests to specific controller methods
    public Claims savePolicies(@RequestBody Claims claims)
    {
        //entering to the log
        log.info("Inside savePolicies methode of the claimsController.");
        return claimsService.saveClaims(claims);
    }

    //methode to update a claims
    //@RequestBody is a Spring Framework annotation used to bind the HTTP request body to a Java object in a RESTful API endpoint
    @PutMapping("/{id}")
    @CircuitBreaker(name = "claimsManagementService",fallbackMethod = "claimsFallback")
    @Retry(name= "claimsManagementService",fallbackMethod = "claimsRetryFallback")
    @RateLimiter(name = "claimsManagementService",fallbackMethod = "claimsRatelimiterFallback")
    //@PostMapping is an annotation used in Java Spring to map HTTP POST requests to specific controller methods
    public Claims updateClaims(@RequestBody Claims claims,@PathVariable("id") long claimsId)
    {
        //entering to the log
        log.info("Inside updatePolicies methode of the claimsController.");
        return claimsService.updateClaims(claims,claimsId);
    }

    //methode to get the claims by id number
    @GetMapping("/{id}")
    @CircuitBreaker(name = "claimsManagementService",fallbackMethod = "claimsFallback")
    @Retry(name= "claimsManagementService",fallbackMethod = "claimsRetryFallback")
    @RateLimiter(name = "claimsManagementService",fallbackMethod = "claimsRatelimiterFallback")
    //@GetMapping is an annotation used in Spring Framework's MVC module to handle HTTP GET requests
    public Claims findclaimsById(@PathVariable("id") long claimsId)
    {
        //entering to the log
        log.info("Inside findclaimsById methode of the claimsController.");
        return claimsService.findClaimsById(claimsId);
    }

    @GetMapping()
    @CircuitBreaker(name = "claimsManagementService",fallbackMethod = "claimsFallback")
    @Retry(name= "claimsManagementService",fallbackMethod = "claimsRetryFallback")
    @RateLimiter(name = "claimsManagementService",fallbackMethod = "claimsRatelimiterFallback")
    //@GetMapping is an annotation used in Spring Framework's MVC module to handle HTTP GET requests
    public List<Claims> GetAllClaims()
    {
        //entering to the log
        log.info("Inside GetAllClaims methode of the claimsController.");
        return claimsService.GetAllClaims();
    }

    //methode to delete a claims
    //@RequestBody is a Spring Framework annotation used to bind the HTTP request body to a Java object in a RESTful API endpoint
    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "claimsManagementService",fallbackMethod = "claimsFallback")
    @Retry(name= "claimsManagementService",fallbackMethod = "claimsRetryFallback")
    @RateLimiter(name = "claimsManagementService",fallbackMethod = "claimsRatelimiterFallback")
    //@PostMapping is an annotation used in Java Spring to map HTTP POST requests to specific controller methods
    public Claims deleteClaims(@PathVariable("id") long claimsId)
    {
        //entering to the log
        log.info("Inside deletePolicies methode of the claimsController.");
        return claimsService.deleteById(claimsId);
    }

    //creating RestTemplateVo to get policy details
    @GetMapping("/policy/{id}")
    @CircuitBreaker(name = "claimsManagementService",fallbackMethod = "claimsFallback")
    @Retry(name= "claimsManagementService",fallbackMethod = "claimsRetryFallback")
    @RateLimiter(name = "claimsManagementService",fallbackMethod = "claimsRatelimiterFallback")
    //@GetMapping is an annotation used in Spring Framework's MVC module to handle HTTP GET requests
    public ResponseTemplate getClaimsWithPolicy(@PathVariable("id") long claimsId)
    {
        return claimsService.getClaimsWithPolicy(claimsId);
    }

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