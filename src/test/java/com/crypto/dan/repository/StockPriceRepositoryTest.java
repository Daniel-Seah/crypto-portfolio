package com.crypto.dan.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockPriceRepositoryTest {
    StockPriceRepository prices;
    @Mock
    Random rng;

    @BeforeEach
    void setUp() {
        when(rng.nextInt(anyInt())).thenReturn(0);
        prices = new StockPriceRepository(rng);
    }

    @Test
    void shouldReturnOneCent_whenRandomIsAtLowerBound() {
        when(rng.nextInt(anyInt())).thenReturn(0);

        BigDecimal price = prices.getPrice("", null);

        assertThat(price).isEqualTo(new BigDecimal("0.01"));
    }

    @Test
    void shouldReturnOneThousand_whenRandomIsAtUpperBound() {
        when(rng.nextInt(anyInt())).thenReturn(99_999);

        BigDecimal price = prices.getPrice("", null);

        assertThat(price).isEqualTo(new BigDecimal("1000.00"));
    }

    @Test
    void shouldReturnRandomAmountInDollars() {
        when(rng.nextInt(anyInt())).thenReturn(12_34);

        BigDecimal price = prices.getPrice("", null);

        assertThat(price).isEqualTo(new BigDecimal("12.35"));
    }
}