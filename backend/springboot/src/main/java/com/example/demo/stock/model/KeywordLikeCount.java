package com.example.demo.stock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("keyword_like_count")
@Getter
@Setter
public class KeywordLikeCount {
    @Id
    private String _id;
    // 키워드
    private String keyword;
    // 갯수
    private int count;

}
