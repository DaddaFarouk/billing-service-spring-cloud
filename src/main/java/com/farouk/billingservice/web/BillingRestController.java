package com.farouk.billingservice.web;

import com.farouk.billingservice.entities.Bill;
import com.farouk.billingservice.feign.CustomerRestClient;
import com.farouk.billingservice.feign.ProductItemRestClient;
import com.farouk.billingservice.models.Customer;
import com.farouk.billingservice.models.Product;
import com.farouk.billingservice.repositories.BillRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BillingRestController {
    private final BillRepository billRepository;
    private final CustomerRestClient customerRestClient;
    private final ProductItemRestClient productItemRestClient;

    public BillingRestController(BillRepository billRepository, CustomerRestClient customerRestClient, ProductItemRestClient productItemRestClient) {
        this.billRepository = billRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill = billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomerById(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(productItem -> {
            Product product = productItemRestClient.getProductById(productItem.getProductID());
            //productItem.setProduct(product);
            productItem.setProductName(product.getName());
        });
        return bill;
    }
}
