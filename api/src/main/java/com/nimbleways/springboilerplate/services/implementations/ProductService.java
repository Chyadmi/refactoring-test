package com.nimbleways.springboilerplate.services.implementations;
import com.nimbleways.springboilerplate.enumeration.ProductType;
import com.nimbleways.springboilerplate.entities.Product;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public interface ProductService {


    @Transactional(isolation = Isolation.SERIALIZABLE)
    //using a higher isolation level would be beneficial, as the products data could change concurrently by other requests.
    void handleProduct(Product product);

    ProductType getProductType();
}