package com.crypto.dan.service;

import com.crypto.dan.model.PortfolioPosition;
import com.crypto.dan.model.PortfolioUpdate;
import com.crypto.dan.model.Position;
import com.crypto.dan.model.StockPrice;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PortfolioEvaluator extends Observable implements Observer {
    private final Map<String, PortfolioPosition> portfolio;

    public PortfolioEvaluator(List<Position> portfolio) {
        this.portfolio = portfolio.stream()
                .map(p -> new PortfolioPosition(p.getSymbol(), BigDecimal.ZERO, p.getQuantity(), BigDecimal.ZERO))
                .collect(Collectors.toMap(PortfolioPosition::getSymbol, Function.identity()));
    }

    public PortfolioUpdate evaluate(List<StockPrice> event) {
        event.forEach(price -> {
            PortfolioPosition pos = portfolio.get(price.getSymbol());
            if (pos == null) {
                return;
            }
            PortfolioPosition update = evaluatePosition(pos, price.getPrice());
            portfolio.put(update.getSymbol(), update);
        });
        Collection<PortfolioPosition> positions = portfolio.values();
        BigDecimal nav = evaluateNav(positions);
        return new PortfolioUpdate(event, positions, nav);
    }

    private static BigDecimal evaluateNav(Collection<PortfolioPosition> positions) {
        return positions.stream()
                .map(PortfolioPosition::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static PortfolioPosition evaluatePosition(PortfolioPosition pos, BigDecimal price) {
        BigDecimal value = price.multiply(pos.getQuantity());
        return new PortfolioPosition(pos.getSymbol(), price, pos.getQuantity(), value);
    }

    @Override
    public void update(Observable o, Object arg) {
        List<StockPrice> event = (List) arg;
        PortfolioUpdate portfolioUpdate = evaluate(event);
        notify(portfolioUpdate);
    }

    private void notify(PortfolioUpdate event) {
        setChanged();
        notifyObservers(event);
    }
}
