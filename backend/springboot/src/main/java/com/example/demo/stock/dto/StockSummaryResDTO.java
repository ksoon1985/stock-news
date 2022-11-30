package com.example.demo.stock.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

/**
 * 종목 코드에 대한 종목 요약 정보
 */
@Getter
@Setter
public class StockSummaryResDTO {
    // 종목 코드
    private String code;
    // 제공 서비스 주요 키워드
    private String[] themeKeywords;
    // 시가 총액
    private String marketCap;
    // 기업 순위
    private String marketRanking;
    // 주식 수
    private String numberOfStocks;
    // 외국인 비율
    private String foreignerRatio;
    // 산업군
    private String industry;
    // 세부산업군
    private String industryDetail;
    // 52주 최고가
    private String highYear;
    // 52주 최저가
    private String lowYear;
    // 기업 개요
    private String[] summaryInfo;
    // 자산 총계
    private String totalAssets;
    // 자본 비중
    private String totalEquityRatio;
    // 부채 비중
    private String deptRatio;
    // 매출 구성
    private ArrayList<Object> salesComposition;
}
