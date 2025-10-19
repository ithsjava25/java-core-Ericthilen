package com.example;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public abstract class Product {

    private final UUID id;
    private final String name;
    private final Category category;
    private BigDecimal price;

    Product(UUID id, String name, Category category, BigDecimal price) {
        this.id = Objects.requireNonNull(id, "ID can't be null");
        this.name = Objects.requireNonNull(name, "Name can't be null");
        this.category = Objects.requireNonNull(category, "Category can't be null");
        this.price = Objects.requireNonNull(price, "Price can't be null");
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Category category() {
        return category;
    }

    public BigDecimal price() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.doubleValue() < 0) throw new IllegalArgumentException("Price can't be null or negative.");
        this.price = price;
    }

    public abstract String productDetails();
}