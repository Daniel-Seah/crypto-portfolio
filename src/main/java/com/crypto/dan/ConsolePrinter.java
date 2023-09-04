package com.crypto.dan;

import com.crypto.dan.model.PortfolioPosition;
import com.crypto.dan.model.PortfolioUpdate;
import com.crypto.dan.model.StockPrice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

@Component
public class ConsolePrinter implements Observer {
    private int counter;
    private final DecimalFormat df;

    public ConsolePrinter() {
        df = new DecimalFormat("#,##0.00");
    }

    @Override
    public void update(Observable o, Object arg) {
        PortfolioUpdate update = (PortfolioUpdate) arg;
        render(update);
    }

    public void render(PortfolioUpdate update) {
        counter++;
        renderTitle();
        renderPriceUpdates(update.getPrices());
        renderPortfolioTitle();
        renderPortfolioPosition(update.getPositions());
        renderPortfolioValue(update.getValue());
    }

    private void renderPortfolioPosition(Collection<PortfolioPosition> positions) {
        positions.forEach(this::renderPosition);
    }

    private void renderPosition(PortfolioPosition p) {
        String price = df.format(p.getPrice());
        String quantity = df.format(p.getQuantity());
        String value = df.format(p.getValue());
        String pos = String.format("%-25s%15s%20s%20s", p.getSymbol(), price, quantity, value);
        System.out.println(pos);
    }

    private void renderPriceUpdates(List<StockPrice> prices) {
        prices.forEach(this::renderPriceUpdate);
    }

    private void renderPriceUpdate(StockPrice p) {
        System.out.println(p.getSymbol() + " change to " + df.format(p.getPrice()));
    }

    private void renderPortfolioValue(BigDecimal value) {
        String nav = df.format(value);
        String portfolioValue = String.format("\n# Total portfolio%63s\n", nav);
        System.out.println(portfolioValue);
    }

    private static void renderPortfolioTitle() {
        String portfolioTitle = "\n## Portfolio";
        System.out.println(portfolioTitle);
        String portfolioheader = "symbol                             price                 qty               value";
        System.out.println(portfolioheader);
    }

    private void renderTitle() {
        String title = String.format("## %d Market Data Update", counter);
        System.out.println(title);
    }
}
