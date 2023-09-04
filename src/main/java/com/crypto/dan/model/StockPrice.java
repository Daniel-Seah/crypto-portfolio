package com.crypto.dan.model;

import java.math.BigDecimal;
import java.util.Objects;

public class StockPrice {
    private final String symbol;
    private final BigDecimal price;

    public StockPrice(String symbol, BigDecimal price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockPrice price1 = (StockPrice) o;

        if (!Objects.equals(symbol, price1.symbol)) return false;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
