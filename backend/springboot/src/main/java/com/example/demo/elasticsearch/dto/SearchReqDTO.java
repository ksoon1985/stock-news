package com.example.demo.elasticsearch.dto;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

// 본 서비스에선 사용안함
@Getter @Setter
public class SearchReqDTO extends PagedReqDTO{
    private List<String> fields; // 검색할 field
    private String searchTerm; // 검색 term
    private String sortBy; // sort 할 field
    private SortOrder order; // sort 방식 (asc,desc)
}
