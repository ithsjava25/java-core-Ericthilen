package com.example;

import java.math.BigDecimal;
import java.util.UUID;

public class ElectronicsProduct extends Product implements Shippable {
    private final int warrantyMonths;
    private final BigDecimal weight;

    protected ElectronicsProduct(UUID id, String name, Category category, BigDecimal price, int warrantyMonths, BigDecimal weight) {
        super(id, name, category, price);
        if (warrantyMonths < 0) throw new IllegalArgumentException("Warranty months cannot be negative.");
        this.warrantyMonths = warrantyMonths;
        this.weight = weight;
    }

    public String productDetails() {
        return "Electronics: " + name() + ", Warranty: " + warrantyMonths + " months";
    }

    public BigDecimal calculateShippingCost() {
        BigDecimal shippingCost = BigDecimal.valueOf(79);
        if (this.weight.compareTo(BigDecimal.valueOf(5.0)) > 0) {
            return shippingCost.add(BigDecimal.valueOf(49));
        } else {
            return shippingCost;
        }
    }

    public double weight() {
        return this.weight.doubleValue();
    }

}