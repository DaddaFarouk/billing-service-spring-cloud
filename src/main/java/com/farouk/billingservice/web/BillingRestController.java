package com.farouk.billingservice.web;

import com.farouk.billingservice.entities.Bill;
import com.farouk.billingservice.feign.ProductItemRestClient;
import com.farouk.billingservice.models.Product;
import com.farouk.billingservice.repositories.BillRepository;
import com.farouk.billingservice.repositories.ProductItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:8888"})
public class BillingRestController {
    private final BillRepository billRepository;
    private final ProductItemRestClient productItemRestClient;
    private final ProductItemRepository productItemRepository;

    public BillingRestController(BillRepository billRepository, ProductItemRestClient productItemRestClient, ProductItemRepository productItemRepository) {
        this.billRepository = billRepository;
        this.productItemRestClient = productItemRestClient;
        this.productItemRepository = productItemRepository;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill = billRepository.findById(id).get();
        bill.getProductItems().forEach(productItem -> {
            Product product = productItemRestClient.getProductById(productItem.getProductID());
            //productItem.setProduct(product);
            productItem.setProductName(product.getName());
        });
        return bill;
    }

    @GetMapping(path = "/fullBills")
    public List<Bill> getBills(){
        List<Bill> bills = billRepository.findAll();
        bills.forEach((bill -> {
            bill.getProductItems().forEach(productItem -> {
                Product product = productItemRestClient.getProductById(productItem.getProductID());
                productItem.setProductName(product.getName());
            });
        }));
        return bills;
    }

    @DeleteMapping(path = "/fullBill/{id}")
    public void deleteBill(@PathVariable(name = "id") Long id){
        Bill bill = billRepository.findById(id).orElseThrow();
        productItemRepository.deleteAll(bill.getProductItems());
        billRepository.delete(bill);
    }
}
