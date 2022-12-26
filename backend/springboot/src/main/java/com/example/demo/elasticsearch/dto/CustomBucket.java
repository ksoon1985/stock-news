package com.example.demo.elasticsearch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomBucket {
    private String keyword;
    private double score;
    private long docCount;
    private long bgCount;
}
