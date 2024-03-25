package com.nimbleways.springboilerplate.services.implementations.impl;


import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.services.implementations.ProductService;

public abstract class AbstractProductService implements ProductService {

    protected final ProductRepository productRepository;
    protected final NotificationService notificationService;


    public AbstractProductService(ProductRepository productRepository, NotificationService notificationService) {
        this.productRepository = productRepository;
        this.notificationService = notificationService;
    }

    protected void decrementProductAvailability(Product product) {
        if (product.getAvailable() > 0) {
            product.setAvailable(product.getAvailable() - 1);
            productRepository.save(product);
        }
    }

    protected void setAvailabilityToZeroAndSave(Product product) {
        product.setAvailable(0);
        productRepository.save(product);
    }

    protected boolean isProductAvailable(Product product) {
        return product.getAvailable() > 0;
    }

}


