package com.farouk.billingservice.feign;

import com.farouk.billingservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = {"*"})
@FeignClient(name = "PRODUCT-SERVICE")
@RepositoryRestResource
public interface ProductItemRestClient {
    @GetMapping(path = "/products")
    PagedModel<Product> pagedProducts(/*@RequestParam("page") int page,
                                      @RequestParam("size") int size*/);
    @GetMapping(path = "/products/{id}")
    Product getProductById(@PathVariable Long id);
}
