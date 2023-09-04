package com.crypto.dan.service;

import com.crypto.dan.EventCaptor;
import com.crypto.dan.repository.StockPriceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockPriceServiceTest {
    @Mock
    Random rng;

    @Mock
    StockPriceRepository repo;

    @Test
    void updateAll_shouldNotEmitEvents_whenStockListingIsEmpty() {
        StockPriceService service = createServiceWith(emptyList());

        List<List<Object>> events = updateAllAndReturnEvents(service);

        assertThat(events).isEmpty();
    }

    @Test
    void updateAll_shouldReturnAllPrices_whenStockListingIsProvided() {
        when(repo.getPrice(any(), any())).thenReturn(BigDecimal.ZERO).thenReturn(BigDecimal.ONE);
        StockPriceService service = createServiceWith(asList("AAPL", "TSLA"));

        List<List<Object>> events = updateAllAndReturnEvents(service);

        assertThat(events.get(0)).extracting("symbol", "price")
                .containsExactly(tuple("AAPL", BigDecimal.ZERO), tuple("TSLA", BigDecimal.ONE));
    }

    @Test
    void update_shouldNotEmitEvents_whenStockListingIsEmpty() {
        StockPriceService service = createServiceWith(emptyList());

        List<List<Object>> events = updateAndReturnEvents(service);

        assertThat(events).isEmpty();
    }

    @Test
    void update_shouldEmitUpdate_whenStockListingIsProvided() {
        when(repo.getPrice(any(), any())).thenReturn(BigDecimal.ONE);
        when(rng.nextInt(anyInt())).thenReturn(1);
        StockPriceService service = createServiceWith(asList("AAPL", "TSLA"));

        List<List<Object>> events = updateAndReturnEvents(service);

        assertThat(events).hasSize(1);
        assertThat(events.get(0)).extracting("symbol", "price")
                .containsExactly(tuple("TSLA", BigDecimal.ONE));
    }

    private List<List<Object>> updateAndReturnEvents(StockPriceService service) {
        EventCaptor<List<Object>> captor = new EventCaptor<>();
        service.addObserver(captor);
        service.update();
        return captor.getEvents();
    }

    private List<List<Object>> updateAllAndReturnEvents(StockPriceService service) {
        EventCaptor<List<Object>> captor = new EventCaptor<>();
        service.addObserver(captor);
        service.updateAll();
        return captor.getEvents();
    }

    private StockPriceService createServiceWith(List<String> stocks) {
        return new StockPriceService(repo, rng, stocks);
    }
}