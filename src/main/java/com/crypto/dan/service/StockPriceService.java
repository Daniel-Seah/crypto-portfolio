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
        List<StockPrice> updates = stocks.stream()
                .map(this::getStockPrice)
                .collect(Collectors.toList());
        notify(updates);
    }

    public void update() {
        if (stocks.isEmpty()) {
            return;
        }
        String randomStock = stocks.get(rng.nextInt(stocks.size()));
        StockPrice stockPrice = getStockPrice(randomStock);
        notify(singletonList(stockPrice));
    }

    private StockPrice getStockPrice(String id) {
        BigDecimal price = prices.getPrice(id, null);
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
