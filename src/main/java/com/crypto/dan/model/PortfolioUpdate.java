package com.crypto.dan.model;

import java.math.BigDecimal;
import java.util.List;

public class PortfolioUpdate {
    private final List<StockPrice> prices;
    private final List<PortfolioPosition> positions;
    private final BigDecimal value;

    public PortfolioUpdate(List<StockPrice> prices, List<PortfolioPosition> positions, BigDecimal value) {
        this.prices = prices;
        this.positions = positions;
        this.value = value;
    }

    public List<StockPrice> getPrices() {
        return prices;
    }

    public List<PortfolioPosition> getPositions() {
        return positions;
    }

    public BigDecimal getValue() {
        return value;
    }

    public static class PortfolioPosition {
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
    }
}
