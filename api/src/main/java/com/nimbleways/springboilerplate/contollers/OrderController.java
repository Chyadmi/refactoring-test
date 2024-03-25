package com.nimbleways.springboilerplate.contollers;

import com.nimbleways.springboilerplate.dto.product.ProcessOrderResponse;
import com.nimbleways.springboilerplate.services.implementations.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping("{orderId}/processOrder")
    @ResponseStatus(HttpStatus.OK)
    public ProcessOrderResponse processOrder(@PathVariable Long orderId) {

        //to refactor this code i plan to implement the factory pattern in combination with the strategy pattern for handling different product types.
        //Each product type will have its own logic encapsulated in a separate bean, which will then be dynamically retrieved as needed
        //the controller should only be responsible for handle the request and forward it to the service layer (Single resposability priciple)

        return orderService.processOrder(orderId);

    }
}
