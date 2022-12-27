package com.example.demo.elasticsearch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PagedReqDTO {
    public static final int DEFAULT_SIZE = 20;

    private int page;
    private int size;
}
