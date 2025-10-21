package com.example;

import java.math.BigDecimal;
import java.util.UUID;

public class ElectronicsProduct extends Product implements Shippable {

    private final int warrantyInMonths;
    private final BigDecimal itemWeight;

    protected ElectronicsProduct(UUID id, String name, Category category, BigDecimal price, int warrantyInMonths, BigDecimal weight) {
        super(id, name, category, price);
        if (warrantyInMonths < 0) {
            throw new IllegalArgumentException("Warranty months cannot be negative.");
        }
        this.warrantyInMonths = warrantyInMonths;
        this.itemWeight = weight;
    }

    @Override
    public String productDetails() {
        return String.format("Electronics: %s, Warranty: %d months", name(), warrantyInMonths);
    }

    @Override
    public BigDecimal calculateShippingCost() {
        final BigDecimal baseCost = BigDecimal.valueOf(79);
        final BigDecimal surcharge = BigDecimal.valueOf(49);
        final BigDecimal threshold = BigDecimal.valueOf(5.0);

        return (itemWeight.compareTo(threshold) > 0)
                ? baseCost.add(surcharge)
                : baseCost;
    }

    @Override
    public double weight() {
        return itemWeight.doubleValue();
    }
}