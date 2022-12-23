package com.example.demo.stock.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

// mongodb collection
@Document("test")
@Getter
@Setter
public class StockPriceModel {
    @Id
    private String _id;
    // 종목 코드
    private String code;
    // 종목 이름
    private String name;
    // 종목 마켓 (코스피,코스닥)
    private String market;
    // 종목 일자별 주가 데이터
    private ArrayList<ArrayList> priceByDate;
}
