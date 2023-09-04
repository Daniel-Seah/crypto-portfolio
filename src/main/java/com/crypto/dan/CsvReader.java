package com.crypto.dan;

import com.crypto.dan.model.Position;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvReader {
    public List<Position> read(InputStream in) {
        boolean isHeader = true;
        List<Position> positions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            while (reader.ready()) {
                String line = reader.readLine();
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                Position p = parsePosition(line);
                positions.add(p);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return positions;
    }

    private Position parsePosition(String line) {
        String[] tokens = line.split(",");
        return new Position(tokens[0], new BigDecimal(tokens[1]));
    }
}
