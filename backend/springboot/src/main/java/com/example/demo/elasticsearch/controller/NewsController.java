package com.example.demo.elasticsearch.controller;

import com.example.demo.elasticsearch.dto.ClusteredNews;
import com.example.demo.elasticsearch.dto.SearchNewsReqDTO;
import com.example.demo.elasticsearch.dto.json.NewsClusteredReqDTO;
import com.example.demo.elasticsearch.dto.json.NewsClusteredResDTO;
import com.example.demo.elasticsearch.model.News;
import com.example.demo.elasticsearch.service.NewsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @Operation(summary = "고유 id 값으로 엘라스틱 서치에서 단일 뉴스 조회")
    @GetMapping("/getNews/{id}")
    public News getNews(@PathVariable String id){
        return newsService.getNewsById(id);
    }


    @Operation(summary = "종목이름, 키워드를 조건으로 엘라스틱 서치에서 뉴스 데이터 요청 (클러스터링 x)",
            description = "종목은 제목에서, 키워드는 내용에서 조회를 합니다.")
    @PostMapping("/getSearchNews")
    public ResponseEntity getSearchNews(@RequestBody SearchNewsReqDTO dto){
        return ResponseEntity.ok().body(newsService.getNews(dto));
    }

    @Operation(summary = "종목이름, 날짜를 조건으로 엘라스틱 서치에서 클러스터링 된 뉴스 데이터 요청")
    @PostMapping("/getClusteredNews")
    public ResponseEntity getClusteredNews(@RequestBody SearchNewsReqDTO newsReqDTO) throws JsonProcessingException {

        ArrayList<ClusteredNews> clusteredNewsList = newsService.getClusteredNews(newsReqDTO);

        return ResponseEntity.ok().body(clusteredNewsList);
    }

}
