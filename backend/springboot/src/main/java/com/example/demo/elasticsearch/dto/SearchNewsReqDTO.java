package com.example.demo.elasticsearch.dto;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SearchNewsReqDTO extends PagedReqDTO{

    private String searchTerm; // 검색 term
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate; // 검색 시작 날짜
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate; // 검색 종료 날짜
    private String sortBy; // sort 할 field
    private SortOrder order; // sort 방식 (asc,desc)

}
