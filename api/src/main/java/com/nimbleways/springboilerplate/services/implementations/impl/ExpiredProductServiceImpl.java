package com.nimbleways.springboilerplate.services.implementations.impl;


import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.enumeration.ProductType;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class ExpiredProductServiceImpl extends AbstractProductService {


    public ExpiredProductServiceImpl(ProductRepository productRepository, NotificationService notificationService) {
        super(productRepository, notificationService);
    }

    @Override
    public void handleProduct(Product product) {
        LocalDate now = LocalDate.now();
        if (isProductAvailableAndNotExpired(product, now)) {
            decrementProductAvailability(product);
        } else {
            handleActuallyExpiredProduct(product);
        }
    }

    private boolean isProductAvailableAndNotExpired(Product product, LocalDate currentDate) {
        return isProductAvailable(product) && product.getExpiryDate().isAfter(currentDate);
    }
    

    private void handleActuallyExpiredProduct(Product product) {
        setAvailabilityToZeroAndSave(product);
        notificationService.sendExpirationNotification(product.getName(), product.getExpiryDate());
    }

    @Override
    public ProductType getProductType() {
        return ProductType.EXPIRABLE;
    }
}
