package com.example.demo.stock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("stock_like")
@Getter
@Setter
public class StockLikeModel {

    @Id
    private String _id;
    // 주식 종목 코드
    private String code;
    // 유저 이메일
    private String email;

    public StockLikeModel(String code, String email){
        this.code = code;
        this.email = email;
    }
}
