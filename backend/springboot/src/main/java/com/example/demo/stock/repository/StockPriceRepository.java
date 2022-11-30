package com.example.demo.stock.repository;

import com.example.demo.stock.model.StockPriceModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockPriceRepository extends MongoRepository<StockPriceModel,String>{
    public StockPriceModel findByCode(String stockCode);
}
