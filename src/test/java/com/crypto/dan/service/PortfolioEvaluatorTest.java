package com.crypto.dan.service;

import com.crypto.dan.EventCaptor;
import com.crypto.dan.model.PortfolioPosition;
import com.crypto.dan.model.PortfolioUpdate;
import com.crypto.dan.model.Position;
import com.crypto.dan.model.StockPrice;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class PortfolioEvaluatorTest {
    @Test
    void shouldForwardStockPriceUpdates() {
        PortfolioEvaluator service = createServiceWith(emptyList());
        List<StockPrice> prices = singletonList(new StockPrice("ABCD", BigDecimal.TEN));

        List<PortfolioUpdate> updates = evaluatePortfolio(service, prices);

        assertThat(updates).first()
                .hasFieldOrPropertyWithValue("prices", singletonList(new StockPrice("ABCD", BigDecimal.TEN)));
    }

    @Test
    void shouldReturnNoPositions_whenPortfolioIsEmpty() {
        PortfolioEvaluator service = createServiceWith(emptyList());
        List<StockPrice> prices = singletonList(new StockPrice("ABCD", BigDecimal.TEN));

        Collection<PortfolioPosition> positions = evaluatePortfolio(service, prices).get(0)
                .getPositions();

        assertThat(positions).isEmpty();
    }

    @Test
    void shouldReturnPositions_whenPortfolioHasPositions() {
        List<Position> positions = asList(new Position("ABCD", BigDecimal.TEN),
                new Position("XYZ", BigDecimal.ONE));
        PortfolioEvaluator service = createServiceWith(positions);
        List<StockPrice> prices = asList(new StockPrice("ABCD", BigDecimal.TEN),
                new StockPrice("XYZ", BigDecimal.TEN));

        Collection<PortfolioPosition> pos = evaluatePortfolio(service, prices).get(0)
                .getPositions();

        PortfolioPosition abcd = new PortfolioPosition("ABCD", BigDecimal.TEN, BigDecimal.TEN, BigDecimal.valueOf(100));
        PortfolioPosition xyz = new PortfolioPosition("XYZ", BigDecimal.TEN, BigDecimal.ONE, BigDecimal.TEN);
        assertThat(pos).containsExactlyInAnyOrder(abcd, xyz);
    }

    @Test
    void shouldReturnNavOf0_whenPortfolioIsEmpty() {
        PortfolioEvaluator service = createServiceWith(emptyList());

        BigDecimal nav = evaluatePortfolio(service, emptyList()).get(0)
                .getValue();

        assertThat(nav).isZero();
    }

    @Test
    void shouldCalculateNav_whenPortfolioHasPositions() {
        List<Position> positions = asList(new Position("ABCD", BigDecimal.TEN),
                new Position("XYZ", BigDecimal.ONE));
        PortfolioEvaluator service = createServiceWith(positions);
        List<StockPrice> prices = asList(new StockPrice("ABCD", BigDecimal.TEN),
                new StockPrice("XYZ", BigDecimal.TEN));

        BigDecimal nav = evaluatePortfolio(service, prices).get(0)
                .getValue();

        assertThat(nav).isEqualTo(BigDecimal.valueOf(110));
    }

    private List<PortfolioUpdate> evaluatePortfolio(PortfolioEvaluator service, List<StockPrice> prices) {
        EventCaptor<PortfolioUpdate> captor = new EventCaptor<>();
        service.addObserver(captor);
        service.update(null, prices);
        return captor.getEvents();
    }

    private PortfolioEvaluator createServiceWith(List<Position> pos) {
        return new PortfolioEvaluator(pos);
    }
}