package com.crypto.dan;

import com.crypto.dan.model.Position;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    List<Position> portfolioPositions(@Value("${positions.file}") String filename,
                                      CsvReader reader) throws IOException {
        File file = ResourceUtils.getFile(filename);
        InputStream in = Files.newInputStream(file.toPath());
        return reader.read(in);
    }

    @Bean
    Random random() {
        return new Random();
    }
}