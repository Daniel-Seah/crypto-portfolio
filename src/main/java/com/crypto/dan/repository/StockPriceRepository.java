package com.crypto.dan.repository;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Random;

@Repository
public class StockPriceRepository {
    private final Random rng;

    public StockPriceRepository(Random rng) {
        this.rng = rng;
    }

    public BigDecimal getPrice() {
        return BigDecimal.valueOf(rng.nextInt(100_000) + 1, 2);
    }
}
