package com.example.demo.elasticsearch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SearchNewsReqDTO extends PagedReqDTO{

    private String searchTerm; // 검색 종목 term ex) 삼성전자
    private String themeKeyword; // 검색 키워드 ex) 반도체
    private String fromDate; // 검색 시작 날짜
    private String toDate; // 검색 종료 날짜
    private String categoryId; // 카테고리 번호

}
