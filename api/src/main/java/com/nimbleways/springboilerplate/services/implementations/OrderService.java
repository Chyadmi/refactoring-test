package com.nimbleways.springboilerplate.services.implementations;


import com.nimbleways.springboilerplate.annotation.Loggable;
import com.nimbleways.springboilerplate.dto.product.ProcessOrderResponse;

public interface OrderService {

    @Loggable
    ProcessOrderResponse processOrder(Long id);
}
