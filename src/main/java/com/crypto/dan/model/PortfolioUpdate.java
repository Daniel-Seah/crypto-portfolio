package com.crypto.dan.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class PortfolioUpdate {
    private final List<StockPrice> prices;
    private final Collection<PortfolioPosition> positions;
    private final BigDecimal value;

    public PortfolioUpdate(List<StockPrice> prices, Collection<PortfolioPosition> positions, BigDecimal value) {
        this.prices = prices;
        this.positions = positions;
        this.value = value;
    }

    public List<StockPrice> getPrices() {
        return prices;
    }

    public Collection<PortfolioPosition> getPositions() {
        return positions;
    }

    public BigDecimal getValue() {
        return value;
    }

}
