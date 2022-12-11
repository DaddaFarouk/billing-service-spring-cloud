package com.farouk.billingservice.feign;

import com.farouk.billingservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8888"})
@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductItemRestClient {
    @GetMapping(path = "/products")
    PagedModel<Product> pagedProducts(/*@RequestParam("page") int page,
                                      @RequestParam("size") int size*/);
    @GetMapping(path = "/products/{id}")
    Product getProductById(@PathVariable Long id);
}
