package com.example.demo.elasticsearch.dto;

import com.example.demo.elasticsearch.model.News;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class ClusteredNews {
    private double score;
    private String label;
    private News news;
    private int count;

    //private ArrayList<News> news;
}
