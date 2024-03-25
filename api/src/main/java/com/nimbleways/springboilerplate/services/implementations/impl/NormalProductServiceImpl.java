package com.nimbleways.springboilerplate.services.implementations.impl;


import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.enumeration.ProductType;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.services.implementations.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class NormalProductServiceImpl extends AbstractProductService {


    public NormalProductServiceImpl(ProductRepository productRepository, NotificationService notificationService) {
        super(productRepository, notificationService);
    }

    @Override
    public void handleProduct(Product product) {
        if (isProductAvailable(product)) {
            decrementProductAvailability(product);
        } else {
            hanleNonAvailableProduct(product);
        }

    }
    private void hanleNonAvailableProduct(Product product) {
        int leadTime = product.getLeadTime();
        if (leadTime > 0) {
            updateProductLeadTimeAndNotifyDelay(leadTime, product);
        }
    }

    private void updateProductLeadTimeAndNotifyDelay(int leadTime, Product product) {
        product.setLeadTime(leadTime);
        //here we should be careful when dealing with a JPA entity passer as param , it's recommended to get the entity each time raother that pass it as param (it's in the hibernate persistence contexte)
        productRepository.save(product);
        //when dealing with notifications it would be better to use an outbox table pattern
        notificationService.sendDelayNotification(leadTime, product.getName());
    }

    @Override
    public ProductType getProductType() {
        return ProductType.NORMAL;
    }
}
