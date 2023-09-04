package com.crypto.dan;

import com.crypto.dan.service.PortfolioEvaluator;
import com.crypto.dan.service.StockPriceService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PortfolioApplication {
    private final StockPriceService priceService;
    private final PortfolioEvaluator evaluator;
    private final ConsolePrinter printer;

    public PortfolioApplication(StockPriceService priceService, PortfolioEvaluator evaluator, ConsolePrinter printer) {
        this.priceService = priceService;
        this.evaluator = evaluator;
        this.printer = printer;
    }

    @PostConstruct
    public void init() {
        priceService.addObserver(evaluator);
        evaluator.addObserver(printer);
        priceService.updateAll();
    }
}
