package com.example;

import java.math.BigDecimal;

public interface Shippable {
    BigDecimal calculateShippingCost();

    double weight();
}