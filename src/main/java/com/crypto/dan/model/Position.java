package com.crypto.dan.model;

import java.math.BigDecimal;

public class Position {
    private final String symbol;
    private final BigDecimal quantity;

    public Position(String symbol, BigDecimal quantity) {
        this.symbol = symbol;
        this.quantity = quantity;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
