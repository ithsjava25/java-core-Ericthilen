package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class FoodProduct extends Product implements Perishable, Shippable {
    private final LocalDate expirationDate;
    private final BigDecimal weight;

    protected FoodProduct(UUID id, String name, Category category, BigDecimal price, LocalDate expirationDate, BigDecimal weight) {
        if (price.doubleValue() < 0) throw new IllegalArgumentException("Price cannot be negative.");
        super(id, name, category, price);
        this.expirationDate = expirationDate;
        if (weight.doubleValue() < 0) throw new IllegalArgumentException("Weight cannot be negative.");
        this.weight = weight;
    }


    public String productDetails() {
        return "Food: " + name() + ", Expires: " + this.expirationDate;
    }

    public LocalDate expirationDate() {
        return this.expirationDate;
    }

    public BigDecimal calculateShippingCost() {
        return this.weight.multiply(BigDecimal.valueOf(50));
    }

    @Override
    public double weight() {
        return this.weight.doubleValue();
    }

}