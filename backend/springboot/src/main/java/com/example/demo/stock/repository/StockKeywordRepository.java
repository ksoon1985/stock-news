package com.example.demo.stock.repository;

import com.example.demo.stock.model.StockKeyword;
import com.example.demo.stock.model.StockLikeModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface StockKeywordRepository  extends MongoRepository<StockKeyword,String> {
    public StockKeyword findByKeywordAndEmail(String keyword, String email);
    public ArrayList<StockKeyword> findByEmail(String email);
}
