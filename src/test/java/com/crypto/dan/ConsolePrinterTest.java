package com.crypto.dan;

import com.crypto.dan.model.PortfolioUpdate;
import com.crypto.dan.model.StockPrice;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class ConsolePrinterTest {
    private ByteArrayOutputStream out;
    private ConsolePrinter printer;

    @BeforeEach
    public void setup() {
        printer = new ConsolePrinter();
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @Test
    void shouldNotPrintContent_whenThereAreNoUpdates() throws IOException {
        assertThat(out.toString()).isEmpty();
    }

    @Test
    void shouldPrintHeaders() {
        PortfolioUpdate update = new PortfolioUpdate(emptyList(), emptyList(), BigDecimal.ZERO);

        printer.render(update);
        printer.render(update);

        assertThat(out.toString()).isEqualToNormalizingNewlines(
                "## 1 Market Data Update\n" +
                        "\n## Portfolio\n" +
                        "symbol                             price                 qty               value\n" +
                        "\n# Total portfolio                                                           0.00\n" +
                        "\n" +
                        "## 2 Market Data Update\n" +
                        "\n## Portfolio\n" +
                        "symbol                             price                 qty               value\n" +
                        "\n# Total portfolio                                                           0.00\n" +
                        "\n");
    }

    @Test
    void shouldPrintStockPriceUpdate() {
        StockPrice apple = new StockPrice("AAPL", BigDecimal.valueOf(110));
        StockPrice tesla = new StockPrice("TESLA", BigDecimal.valueOf(123456, 2));
        PortfolioUpdate update = new PortfolioUpdate(asList(apple, tesla), emptyList(), BigDecimal.ZERO);

        printer.render(update);

        assertThat(out.toString()).isEqualToNormalizingNewlines("## 1 Market Data Update\n" +
                "AAPL change to 110.00\n" +
                "TESLA change to 1,234.56\n" +
                "\n## Portfolio\n" +
                "symbol                             price                 qty               value\n" +
                "\n# Total portfolio                                                           0.00\n" +
                "\n");
    }

    @Test
    void shouldPrintPortfolioPositions() {
        PortfolioUpdate.PortfolioPosition apple = new PortfolioUpdate.PortfolioPosition("AAPL", BigDecimal.valueOf(110), BigDecimal.valueOf(1_000), BigDecimal.valueOf(110_000));
        PortfolioUpdate.PortfolioPosition tesla = new PortfolioUpdate.PortfolioPosition("TSLA", BigDecimal.valueOf(450), BigDecimal.valueOf(-10_000), BigDecimal.valueOf(-4_500_000));
        PortfolioUpdate update = new PortfolioUpdate(emptyList(), asList(apple, tesla), BigDecimal.ZERO);

        printer.render(update);

        assertThat(out.toString()).isEqualToNormalizingNewlines("## 1 Market Data Update\n" +
                "\n## Portfolio\n" +
                "symbol                             price                 qty               value\n" +
                "AAPL                              110.00            1,000.00          110,000.00\n" +
                "TSLA                              450.00          -10,000.00       -4,500,000.00\n" +
                "\n# Total portfolio                                                           0.00\n" +
                "\n");
    }

    @Test
    void shouldPrintPortfolioValue() {
        PortfolioUpdate update = new PortfolioUpdate(emptyList(), emptyList(), BigDecimal.valueOf(-1_234_567_895, 3));

        printer.render(update);

        assertThat(out.toString()).isEqualToNormalizingNewlines("## 1 Market Data Update\n" +
                "\n## Portfolio\n" +
                "symbol                             price                 qty               value\n" +
                "\n# Total portfolio                                                  -1,234,567.90\n" +
                "\n");
    }

    @AfterEach
    public void teardown() {
        System.setOut(System.out);
    }
}