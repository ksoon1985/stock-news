package com.example.demo.elasticsearch.controller;

import com.example.demo.elasticsearch.dto.SearchNewsReqDTO;
import com.example.demo.elasticsearch.model.News;
import com.example.demo.elasticsearch.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
//import org.carrot2.language.korean.KoreanLanguageComponents;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
//@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/getNews/{id}")
    public News getNews(@PathVariable String id){
        return newsService.getNewsById(id);
    }


    @Operation(summary = "종목이름 과 날짜를 조건으로한 뉴스 데이터 요청",description = "searchTerm 은 종목이름 , fromDate 는 언제부터 , toDate 는 언제까지의 조건입니다. 최종적으로 조건에 맞는 뉴스 데이터를 응답합니다.")
    @PostMapping("/getSearchNews")
    public ResponseEntity getSearchNews(@RequestBody SearchNewsReqDTO dto){
        System.out.println("######"+dto.getSearchTerm()+"######"+dto.getFromDate());
        return ResponseEntity.ok().body(newsService.getNews(dto));
    }


    @GetMapping("/getClusteredNews")
    public String getTest(){
        //KoreanLanguageComponents koreanLanguageComponents = new KoreanLanguageComponents();
        //return newsService.getClusteredNews();
        return null;

    }
}
