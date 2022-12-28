package com.example.demo;

import com.example.demo.stock.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

//@SpringBootTest
public class StatisticsTest {

//    @Autowired
//    StockService stockService;

    @Test
    void stockClickCountProgress(){
//        String stockCode = "005930";
//
//        stockService.getClickCountProgress(stockCode);

        // 1주 전 ~ 현재 날짜 리스트
        ArrayList<String> recent1WeekDateList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE,-6);

        for(int i=1;i<=7;i++){
            recent1WeekDateList.add(format.format(cal.getTime()));
            cal.add(Calendar.DATE,1);
        }

    }
}
