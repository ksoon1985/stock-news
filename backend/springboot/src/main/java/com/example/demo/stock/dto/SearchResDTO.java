package com.example.demo.stock.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 종목 코드로 검색 시 응답할 데이터 정보
 */
@Getter
@Setter
public class SearchResDTO {
    // 종목 코드
    private String code;
    // 종목 이름
    private String name;
    // 종목 마켓 (코스피,코스닥)
    private String market;
}
