package com.example.demo.elasticsearch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomBucket {
    private String keyword;
    private double score;
    private long docCount; // 주어진 용어를 가진 결과 세트의 문서 수
    private long bgCount; // 주어진 용어를 가진 전체 색인의 문서 수
}
