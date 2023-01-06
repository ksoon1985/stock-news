package com.example.demo.elasticsearch.dto;

import com.example.demo.elasticsearch.model.News;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ClusteredNews {
    private double score;
    private String label;
    private List<String> phrases;
    private News news;
    private List<News> newsList;
    private int count;

}
