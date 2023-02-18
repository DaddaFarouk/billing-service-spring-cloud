package com.farouk.billingservice.feign;

import com.farouk.billingservice.models.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@CrossOrigin(origins = {"*"})
@FeignClient(name = "CUSTOMER-SERVICE")
@RepositoryRestResource
public interface CustomerRestClient {
    @GetMapping(path = "/customers/{id}")
    Customer getCustomerById(@PathVariable(name="id") Long id);

    @GetMapping(path = "/customers")
    ArrayList<Customer> getAllCustomers();
}
