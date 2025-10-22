package com.example;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Category {
    private static final Map<String, Category> CATEGORIES = new ConcurrentHashMap<>();
    private final String name;

    private Category(String name) {
        if (name == null) throw new IllegalArgumentException("Category name can't be null");
        if (name.isBlank()) throw new IllegalArgumentException("Category name can't be blank");
        this.name = normalizedName(name);

    }

    public static Category of(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Category name can't be blank");
        }
        name = normalizedName(name);
        return CATEGORIES.computeIfAbsent(name, Category::new);
    }

    public String getName() {
        return name;
    }

    public static String normalizedName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

}