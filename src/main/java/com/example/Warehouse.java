package com.example;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Warehouse {
    private static final Map<String, Warehouse> WAREHOUSES = new ConcurrentHashMap<>();

    private final String name;
    private final Map<UUID, Product> inventory;
    private final Set<UUID> changedItems;

    private Warehouse(String name) {
        this.name = name;
        this.inventory = new ConcurrentHashMap<>();
        this.changedItems = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public static Warehouse getInstance(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Warehouse name must be provided");
        }
        return WAREHOUSES.computeIfAbsent(name, Warehouse::new);
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (inventory.putIfAbsent(product.id(), product) == null) {
            changedItems.add(product.id());
        }
    }

    public List<Product> getProducts() {
        return inventory.values().stream().toList();
    }

    public Optional<Product> getProductById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(inventory.get(id));
    }

    public Map<Category, List<Product>> getProductsGroupedByCategories() {
        return inventory.values()
                .stream()
                .collect(Collectors.groupingBy(Product::category));
    }

    public void clearProducts() {
        Warehouse warehouse = WAREHOUSES.get(name);
        if (warehouse != null) {
            warehouse.inventory.clear();
            warehouse.changedItems.clear();
        }
    }

    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    public void updateProductPrice(UUID id, BigDecimal newPrice) {
        Product product = getProductById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found with id: " + id));
        product.setPrice(newPrice);
        changedItems.add(id);
    }

    public List<Product> getChangedProducts() {
        return changedItems.stream()
                .filter(inventory::containsKey)
                .map(inventory::get)
                .toList();
    }

    public List<Perishable> expiredProducts() {
        return inventory.values().stream()
                .filter(p -> p instanceof Perishable)
                .map(p -> (Perishable) p)
                .filter(Perishable::isExpired)
                .collect(Collectors.toList());
    }

    public List<Shippable> shippableProducts() {
        return inventory.values().stream()
                .filter(p -> p instanceof Shippable)
                .map(p -> (Shippable) p)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void remove(UUID id) {
        if (id != null) {
            inventory.remove(id);
            changedItems.remove(id);
        }
    }
}