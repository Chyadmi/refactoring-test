package com.nimbleways.springboilerplate.services.implementations.impl;


import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.enumeration.ProductType;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;


@Service
public class SeasonalProductServiceImpl extends AbstractProductService {

    public SeasonalProductServiceImpl(ProductRepository productRepository, NotificationService notificationService) {
        super(productRepository, notificationService);
    }

    @Override
    public void handleProduct(Product product) {

        if (isProductAvailableAndInSeason(product)) {
            decrementProductAvailability(product);
        } else {
            handleNonAvailableOrOutOfSeasonProduct(product);
        }

    }

    private boolean isProductAvailableAndInSeason(Product product) {
        return LocalDate.now().isAfter(product.getSeasonStartDate()) &&
                LocalDate.now().isBefore(product.getSeasonEndDate()) &&
                product.getAvailable() > 0;
    }

    private void handleNonAvailableOrOutOfSeasonProduct(Product product) {
        
        if (isProductOutOfStockAfterLeadTime(product)) {
            sendOutOfStockAndSetZero(product);
        } else if (isProductSeasonStarted(product)) {
            sendOutOfStockAndSave(product);
        } else {
            product.setLeadTime(product.getLeadTime());
            productRepository.save(product);
            notificationService.sendDelayNotification(product.getLeadTime(), product.getName());
        }
    }

    private boolean isProductOutOfStockAfterLeadTime(Product product) {
        return LocalDate.now().plusDays(product.getLeadTime()).isAfter(product.getSeasonEndDate());
    }

    private void sendOutOfStockAndSetZero(Product product) {
        setAvailabilityToZeroAndSave(product);
        notificationService.sendOutOfStockNotification(product.getName());
    }

    private boolean isProductSeasonStarted(Product product) {
        return product.getSeasonStartDate().isAfter(LocalDate.now());
    }

    private void sendOutOfStockAndSave(Product product) {
        notificationService.sendOutOfStockNotification(product.getName());
        productRepository.save(product);
    }

    @Override
    public ProductType getProductType() {
        return ProductType.SEASONAL;
    }
}
