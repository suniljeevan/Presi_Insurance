package com.insurance.BillingManagement.controller;

import com.insurance.BillingManagement.ResponceTemplate.Response;
import com.insurance.BillingManagement.entity.Bills;
import com.insurance.BillingManagement.service.BillsService;
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
@RequestMapping("/bills")
//@RequestMapping is a Spring Framework annotation used to map HTTP requests to specific methods in a controller
@Slf4j
//It's a shorthand for declaring a logger instance for the class, which can then be used to log messages and other information.
public class BillsController
{
    @Autowired
    //When @Autowired is applied to a field, Spring will automatically inject the dependency at runtime.
    private BillsService billsService;

    //methode to create a Bills
    //@RequestBody is a Spring Framework annotation used to bind the HTTP request body to a Java object in a RESTful API endpoint
    @PostMapping("/")
    @CircuitBreaker(name = "billsManagementService",fallbackMethod = "billsFallback")
    @Retry(name= "billsManagementService",fallbackMethod = "billsRetryFallback")
    @RateLimiter(name = "billsManagementService",fallbackMethod = "billsRatelimiterFallback")
    //@PostMapping is an annotation used in Java Spring to map HTTP POST requests to specific controller methods
    public Bills savePolicies(@RequestBody Bills bills)
    {
        //entering to the log
        log.info("Inside savePolicies methode of the BillsController.");
        return billsService.saveBills(bills);
    }

    //methode to update a Bills
    //@RequestBody is a Spring Framework annotation used to bind the HTTP request body to a Java object in a RESTful API endpoint
    @PutMapping("/{id}")
    @CircuitBreaker(name = "billsManagementService",fallbackMethod = "billsFallback")
    @Retry(name= "billsManagementService",fallbackMethod = "billsRetryFallback")
    @RateLimiter(name = "billsManagementService",fallbackMethod = "billsRatelimiterFallback")
    //@PostMapping is an annotation used in Java Spring to map HTTP POST requests to specific controller methods
    public Bills updateBills(@RequestBody Bills bills,@PathVariable("id") long billsId)
    {
        //entering to the log
        log.info("Inside updatePolicies methode of the BillsController.");
        return billsService.updateBills(bills,billsId);
    }

    //methode to get the Bills by id number
    @GetMapping("/{id}")
    @CircuitBreaker(name = "billsManagementService",fallbackMethod = "billsFallback")
    @Retry(name= "billsManagementService",fallbackMethod = "billsRetryFallback")
    @RateLimiter(name = "billsManagementService",fallbackMethod = "billsRatelimiterFallback")
    //@GetMapping is an annotation used in Spring Framework's MVC module to handle HTTP GET requests
    public Bills findBillsById(@PathVariable("id") long billsId)
    {
        //entering to the log
        log.info("Inside findBillsById methode of the BillsController.");
        return billsService.findBillsById(billsId);
    }

    @GetMapping()
    @CircuitBreaker(name = "billsManagementService",fallbackMethod = "billsFallback")
    @Retry(name= "billsManagementService",fallbackMethod = "billsRetryFallback")
    @RateLimiter(name = "billsManagementService",fallbackMethod = "billsRatelimiterFallback")
    //@GetMapping is an annotation used in Spring Framework's MVC module to handle HTTP GET requests
    public List<Bills> GetAllBills()
    {
        //entering to the log
        log.info("Inside GetAllBills methode of the BillsController.");
        return billsService.GetAllBills();
    }

    //methode to delete a Bills
    //@RequestBody is a Spring Framework annotation used to bind the HTTP request body to a Java object in a RESTful API endpoint
    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "billsManagementService",fallbackMethod = "billsFallback")
    @Retry(name= "billsManagementService",fallbackMethod = "billsRetryFallback")
    @RateLimiter(name = "billsManagementService",fallbackMethod = "billsRatelimiterFallback")
    //@PostMapping is an annotation used in Java Spring to map HTTP POST requests to specific controller methods
    public Bills deleteBills(@PathVariable("id") long billsId)
    {
        //entering to the log
        log.info("Inside deletePolicies methode of the BillsController.");
        return billsService.deleteById(billsId);
    }

    //creating RestTemplateVo to get policy details
    @GetMapping("/alldetails/{id}")
    @CircuitBreaker(name = "billsManagementService",fallbackMethod = "billsFallback")
    @Retry(name= "billsManagementService",fallbackMethod = "billsRetryFallback")
    @RateLimiter(name = "billsManagementService",fallbackMethod = "billsRatelimiterFallback")
    //@GetMapping is an annotation used in Spring Framework's MVC module to handle HTTP GET requests
    public Response getClaimsAndPolicyWithBills(@PathVariable("id") long claimsId)
    {
        return billsService.getClaimsAndPolicyWithBills(claimsId);
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