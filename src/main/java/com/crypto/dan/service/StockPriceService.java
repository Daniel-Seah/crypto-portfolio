package com.crypto.dan.service;

import com.crypto.dan.model.StockPrice;
import com.crypto.dan.repository.StockPriceRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

public class StockPriceService extends Observable {
    private final StockPriceRepository prices;
    private final Random rng;
    private final List<String> stocks;

    public StockPriceService(StockPriceRepository prices,
                             Random rng,
                             List<String> stocks) {
        this.prices = prices;
        this.rng = rng;
        this.stocks = stocks;
    }

    public void updateAll() {
        List<StockPrice> updates = updateAllStockPrices();
        notify(updates);
    }

    public void update() {
        StockPrice stockPrice = updateRandomStockPrice();
        if (stockPrice == null) {
            return;
        }
        notify(singletonList(stockPrice));
    }

    private List<StockPrice> updateAllStockPrices() {
        return stocks.stream()
                .map(this::getStockPrice)
                .collect(Collectors.toList());
    }

    private StockPrice updateRandomStockPrice() {
        if (stocks.isEmpty()) {
            return null;
        }
        String randomStock = stocks.get(rng.nextInt(stocks.size()));
        return getStockPrice(randomStock);
    }

    private StockPrice getStockPrice(String id) {
        BigDecimal price = prices.getPrice();
        return new StockPrice(id, price);
    }

    private void notify(List<StockPrice> updates) {
        if (updates.isEmpty()) {
            return;
        }
        setChanged();
        notifyObservers(updates);
    }
}
