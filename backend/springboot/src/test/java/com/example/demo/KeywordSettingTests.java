package com.example.demo;

import com.example.demo.stock.model.StockInfoModel;
import com.example.demo.stock.repository.StockInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class KeywordSettingTests {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private StockInfoRepository stockInfoRepository;

    @Test
    void keywordSettingTests() {
        List<StockInfoModel> all = stockInfoRepository.findAll();

        HashSet<String> hashSet = new HashSet<>();
        for (StockInfoModel stockInfoModel : all) {
            String[] themeKeywords = stockInfoModel.getThemeKeywords();
            for (String themeKeyword : themeKeywords) {
                hashSet.add(themeKeyword);
            }
        }

    }

}
