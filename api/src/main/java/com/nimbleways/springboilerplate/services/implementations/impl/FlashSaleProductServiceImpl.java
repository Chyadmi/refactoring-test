package com.nimbleways.springboilerplate.services.implementations.impl;

import com.nimbleways.springboilerplate.entities.FlashSaleProduct;
import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.enumeration.ProductType;
import com.nimbleways.springboilerplate.exception.InvalidProductTypeException;
import com.nimbleways.springboilerplate.repositories.ProductRepository;
import com.nimbleways.springboilerplate.services.implementations.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Predicate;

@Service
public class FlashSaleProductServiceImpl extends AbstractProductService {

    private final Predicate<FlashSaleProduct> isProductFlashSaleActive = flashSaleProduct ->
            LocalDate.now().isAfter(flashSaleProduct.getFlashSaleStartDate()) &&
                    LocalDate.now().isBefore(flashSaleProduct.getFlashSaleEndDate());

    private final Predicate<FlashSaleProduct> notExceedMaxQuantity = flashSaleProduct ->
            flashSaleProduct.getFlashSaleQuantitySold() < flashSaleProduct.getFlashSaleMaxQuantity();
    
    public FlashSaleProductServiceImpl(ProductRepository productRepository, NotificationService notificationService) {
        super(productRepository, notificationService);
    }

    @Override
    public void handleProduct(Product product) {
        if (!(product instanceof FlashSaleProduct flashSaleProduct)) {
            throw new InvalidProductTypeException("Product with ID: " + product.getId() + " is not a FlashSaleProduct");
        }

        if (isProductAvailable(flashSaleProduct) && isProductFlashSaleActive.and(notExceedMaxQuantity).test(flashSaleProduct)) {
            sellProduct(flashSaleProduct);
        } else {
            markProductAsUnavailable(flashSaleProduct);
        }
    }

    private void sellProduct(FlashSaleProduct flashSaleProduct) {
        flashSaleProduct.setFlashSaleQuantitySold(flashSaleProduct.getFlashSaleQuantitySold() + 1);
        decrementProductAvailability(flashSaleProduct);
    }

    @Override
    public ProductType getProductType() {
        return ProductType.FLASHSALE;
    }

    private void markProductAsUnavailable(FlashSaleProduct flashSaleProduct) {
        notificationService.sendOutOfStockNotification(flashSaleProduct.getName());
    }
}
