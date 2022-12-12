package com.farouk.billingservice;

import com.farouk.billingservice.entities.Bill;
import com.farouk.billingservice.entities.ProductItem;
import com.farouk.billingservice.feign.CustomerRestClient;
import com.farouk.billingservice.feign.ProductItemRestClient;
import com.farouk.billingservice.models.Customer;
import com.farouk.billingservice.models.Product;
import com.farouk.billingservice.repositories.BillRepository;
import com.farouk.billingservice.repositories.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemRepository productItemRepository,
                            CustomerRestClient customerRestClient,
                            ProductItemRestClient productItemRestClient
                            ){
        return args -> {
            for (long i = 1L; i < 6L; i++) {
                Customer customer = customerRestClient.getCustomerById(i);
                Bill bill = billRepository.save(new Bill(null, new Date(),null,customer.getId(),0));
                Collection<ProductItem> productItems = new ArrayList<>();
                PagedModel<Product> pagedProducts = productItemRestClient.pagedProducts();
                pagedProducts.forEach(product -> {
                    if(Math.random()>=0.5){
                    ProductItem productItem = new ProductItem();
                    productItem.setProductName(product.getName());
                    productItem.setQuantity(1 + new Random().nextInt(15));
                        productItem.setPrice(product.getPrice()*productItem.getQuantity());
                    productItem.setBill(bill);
                    productItem.setProductID(product.getId());
                    productItemRepository.save(productItem);
                    productItems.add(productItem);
                    bill.setTotalPrice(bill.getTotalPrice()+productItem.getPrice());
                    }
                });
                bill.setProductItems(productItems);
                bill.getProductItems().forEach(productItem -> {
                });
                billRepository.save(bill);
            }
        };
    }
}
