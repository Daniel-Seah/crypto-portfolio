package com.crypto.dan;

import com.crypto.dan.service.StockPriceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("TEST")
public class ApplicationTest {
    @MockBean
    Random rng;
    @Autowired
    StockPriceService prices;
    private ByteArrayOutputStream out;

    @BeforeEach
    public void setup() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    void shouldPrintPortfolioPositions() {
        when(rng.nextInt(anyInt())).thenReturn(1);

        prices.updateAll();
        prices.update();

        assertThat(out.toString()).isEqualToNormalizingNewlines(
                "## 2 Market Data Update\n" +
                        "AAPL change to 0.02\n" +
                        "TELSA change to 0.02\n" +
                        "\n## Portfolio\n" +
                        "symbol                             price                 qty               value\n" +
                        "AAPL                                0.02            1,000.00               20.00\n" +
                        "TELSA                               0.02             -500.00              -10.00\n" +
                        "\n# Total portfolio                                                          10.00\n" +
                        "\n" +
                        "## 3 Market Data Update\n" +
                        "TELSA change to 0.02\n" +
                        "\n## Portfolio\n" +
                        "symbol                             price                 qty               value\n" +
                        "AAPL                                0.02            1,000.00               20.00\n" +
                        "TELSA                               0.02             -500.00              -10.00\n" +
                        "\n# Total portfolio                                                          10.00\n" +
                        "\n");
    }

    @AfterEach
    public void teardown() {
        System.setOut(System.out);
    }
}
