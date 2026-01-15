package com.example.TechMarket.OrderStatus;

public enum OrderStatus {

    IN_REVIEW("В обработка"),
    SENT("Изпратена"),
    DELIVERED("Доставена");

    private final String displayName;

    OrderStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

