package com.farouk.billingservice.repositories;

import com.farouk.billingservice.entities.Bill;
import com.farouk.billingservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Collection;
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8888"})
@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {
    void deleteAllByBill(Bill bill);
}
