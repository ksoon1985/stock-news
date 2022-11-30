package com.example.demo.stock.repository;

import com.example.demo.stock.model.StockLikeModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface StockLikeRepository extends MongoRepository<StockLikeModel,String> {
    public StockLikeModel findByCodeAndEmail(String stockCode,String email);
    public ArrayList<StockLikeModel> findByEmail(String email);
}
