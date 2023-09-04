package com.crypto.dan.model;

import java.math.BigDecimal;
import java.util.Objects;

public class PortfolioPosition {
    private final String symbol;
    private final BigDecimal price;
    private final BigDecimal quantity;
    private final BigDecimal value;

    public PortfolioPosition(String symbol, BigDecimal price, BigDecimal quantity, BigDecimal value) {
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PortfolioPosition that = (PortfolioPosition) o;

        if (!Objects.equals(symbol, that.symbol)) return false;
        if (!Objects.equals(price, that.price)) return false;
        if (!Objects.equals(quantity, that.quantity)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        int result = symbol != null ? symbol.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PortfolioPosition{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", value=" + value +
                '}';
    }
}
