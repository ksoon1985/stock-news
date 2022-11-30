package com.example.demo.stock.repository;

import com.example.demo.stock.model.StockInfoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockInfoRepository extends MongoRepository<StockInfoModel,String> {
    public StockInfoModel findByCode(String stockCode);
}
