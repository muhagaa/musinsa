package com.musinsa.item;

import com.musinsa.item.domain.Item;
import com.musinsa.item.repository.ItemRepository;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DatabaseInitializer implements ApplicationRunner {
    private final ItemRepository itemRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            List<String> lines = this.readCSV();
            for (String line : lines) {
                String[] str = line.split(",");
                itemRepository.save(new Item(str[0],str[1],Long.parseLong(str[2])));
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    private List<String> readCSV() throws IOException {
        List<String> lines = new ArrayList<>();
        InputStreamReader in = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("item.csv"), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(in);
        String line;
        while ((line = br.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }
}
