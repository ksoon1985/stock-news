package com.example.demo.elasticsearch.model;

import com.example.demo.elasticsearch.utils.Indices;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
@Document(indexName = Indices.NEWS_INDEX)
public class News {

    @Id
    private String id;

    private String article_id;

    private String category_id;

    private String content;

    private String journalist_name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date modify_date;

    private String office_id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registration_date;

    private String title;

}
