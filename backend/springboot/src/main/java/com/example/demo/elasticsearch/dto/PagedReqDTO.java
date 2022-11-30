package com.example.demo.elasticsearch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PagedReqDTO {
    private static final int DEFAULT_SIZE = 100;

    private int page;
    private int size;
}
