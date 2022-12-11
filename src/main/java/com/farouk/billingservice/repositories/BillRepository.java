package com.farouk.billingservice.repositories;

import com.farouk.billingservice.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8888"})
@RepositoryRestResource
public interface BillRepository extends JpaRepository<Bill, Long> {
}
