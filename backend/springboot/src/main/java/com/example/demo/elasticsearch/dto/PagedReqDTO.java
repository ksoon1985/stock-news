package com.example.demo.elasticsearch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PagedReqDTO {
    public static final int DEFAULT_SIZE = 50;

    public static final int CLUSTER_DEFAULT_SIZE = 100;

    private int page;
    private int size;
}
