package com.example.demo.stock.repository;

import com.example.demo.stock.model.StockPriceModel;
import com.example.demo.stock.model.StockStatistics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockStatisticsRepository extends MongoRepository<StockStatistics,String> {
}
