package com.nimbleways.springboilerplate.services.implementations.impl;


import com.nimbleways.springboilerplate.dto.product.ProcessOrderResponse;
import com.nimbleways.springboilerplate.entities.Order;
import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.enumeration.ProductType;
import com.nimbleways.springboilerplate.exception.OrderNotFoundException;
import com.nimbleways.springboilerplate.repositories.OrderRepository;
import com.nimbleways.springboilerplate.services.implementations.OrderService;
import com.nimbleways.springboilerplate.services.implementations.ProductService;
import com.nimbleways.springboilerplate.services.implementations.ProductServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductServiceFactory productServiceFactory;
    @Override
    @Transactional
    public ProcessOrderResponse processOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));

        Set<Product> products = order.getItems();
        //get the bean that will handle the product using product type
        products.forEach(product -> {
            // Convert the string to enum
            ProductType productType = ProductType.valueOf(product.getType().toUpperCase());
            ProductService service = productServiceFactory.getProductService(productType);
            service.handleProduct(product);
        });

        return new ProcessOrderResponse(order.getId());

    }
}
