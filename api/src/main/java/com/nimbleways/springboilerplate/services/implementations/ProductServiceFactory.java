package com.nimbleways.springboilerplate.services.implementations;

import com.nimbleways.springboilerplate.enumeration.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;


//Factory class that will generate the beans base on a strategy
@Component
public class ProductServiceFactory {

    private final Map<ProductType, ProductService> productServiceStrategies = new EnumMap<>(ProductType.class);


    //initialize the Map will the beasn that will be used later
    public ProductServiceFactory(Set<ProductService> services) {
        services.forEach(service -> productServiceStrategies.put(service.getProductType(), service));
    }

    public ProductService getProductService(ProductType productType) {
        ProductService service = productServiceStrategies.get(productType);
        if (service == null) {
            throw new IllegalArgumentException("No ProductService found for type: " + productType);
        }
        return service;
    }
}
