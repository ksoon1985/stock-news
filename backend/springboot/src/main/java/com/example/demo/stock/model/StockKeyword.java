package com.example.demo.stock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("keyword_like")
@Getter
@Setter
public class StockKeyword {
    @Id
    private String _id;
    // 키워드
    private String keyword;
    // 유저 이메일
    private String email;

    public StockKeyword(String keyword, String email){
        this.keyword = keyword;
        this.email = email;
    }
}
