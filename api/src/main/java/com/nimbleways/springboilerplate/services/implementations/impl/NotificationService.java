package com.nimbleways.springboilerplate.services.implementations.impl;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NotificationService {

    public void sendDelayNotification(int leadTime, String productName) {
    }

    public void sendOutOfStockNotification(String productName) {
    }

    public void sendExpirationNotification(String productName, LocalDate expiryDate) {
    }
}
