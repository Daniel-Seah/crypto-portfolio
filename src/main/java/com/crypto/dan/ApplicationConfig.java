package com.crypto.dan;

import com.crypto.dan.service.StockPriceService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Profile("!TEST")
@EnableScheduling
public class ApplicationConfig {
    StockPriceService priceService;

    public ApplicationConfig(StockPriceService priceService) {
        this.priceService = priceService;
    }

    @Scheduled(fixedDelay = 2000)
    public void update() {
        priceService.update();
    }
}
