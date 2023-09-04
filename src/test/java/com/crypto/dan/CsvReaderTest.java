package com.crypto.dan;

import com.crypto.dan.model.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class CsvReaderTest {
    CsvReader reader;

    @BeforeEach
    void setUp() {
        reader = new CsvReader();
    }

    @Test
    void shouldReturnEmptyList_whenFileIsBlank() {
        InputStream s = createStream("");

        List<Position> positions = reader.read(s);

        assertThat(positions).isEmpty();
    }

    @Test
    void shouldReturnEmptyList_whenFileContainsOnlyHeader() {
        InputStream s = createStream("symbol,positionSize");

        List<Position> positions = reader.read(s);

        assertThat(positions).isEmpty();
    }

    @Test
    void shouldReturnStockPositions_whenFileContainsOnlyStocks() {
        InputStream s = createStream("symbol,positionSize\n" +
                "AAPL,1000\n" +
                "TESLA,-500");

        List<Position> positions = reader.read(s);

        assertThat(positions).extracting("symbol", "quantity")
                .containsExactly(tuple("AAPL", BigDecimal.valueOf(1000)),
                        tuple("TESLA", BigDecimal.valueOf(-500)));
    }

    private static InputStream createStream(String content) {
        return new ByteArrayInputStream(content.getBytes());
    }


}