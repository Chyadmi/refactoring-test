package com.nimbleways.springboilerplate.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum ProductType {
    NORMAL("Normal"),
    SEASONAL("Seasonal"),
    EXPIRABLE("Expirable"),
    FLASHSALE("FlashSale");

    private String type;
    private static final Map<String, ProductType> stringToEnumMap = new HashMap<>();

    static {
        for (ProductType type : values()) {
            stringToEnumMap.put(type.toString(), type);
        }
    }

    ProductType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

    public static ProductType fromString(String type) {
        if (stringToEnumMap.containsKey(type)) {
            return stringToEnumMap.get(type);
        }
        throw new IllegalArgumentException("No constant with text " + type + " found");
    }
}
