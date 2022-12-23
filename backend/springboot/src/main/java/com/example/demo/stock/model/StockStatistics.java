package com.example.demo.stock.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Document("stock_statistics")
@Getter @Setter
public class StockStatistics {
    @Id
    private String _id;
    // 종목 코드
    private String stockCode;
    // 종목 이름
    private String stockName;
    // 유저 이메일
    private String email;
    // 유저 성별
    private String gender;
    // 유저 생년월일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthDay;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime searchDate;
}
