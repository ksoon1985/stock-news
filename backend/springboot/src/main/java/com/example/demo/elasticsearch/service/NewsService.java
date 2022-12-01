package com.example.demo.elasticsearch.service;

import com.example.demo.elasticsearch.dto.SearchNewsReqDTO;
import com.example.demo.elasticsearch.dto.SearchReqDTO;
import com.example.demo.elasticsearch.model.News;
import com.example.demo.elasticsearch.utils.Indices;
import com.example.demo.elasticsearch.utils.SearchUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
//@Service
@RequiredArgsConstructor
public class NewsService {

    private final ObjectMapper MAPPER = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();
    private final RestHighLevelClient client;

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private String port;

    // elasticsearch news id 로 news 정보 받아오기
    public News getNewsById(String newsId) {
        try {
            GetResponse documentFields = client.get(new GetRequest(Indices.NEWS_INDEX, newsId), RequestOptions.DEFAULT);

            if (documentFields == null || documentFields.isSourceEmpty()) {
                return null;
            }

            // json 형태를 객체 형태로 반환
            return MAPPER.readValue(documentFields.getSourceAsString(), News.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // news cluster 값 조회 << 미구현 >>
    public String getClusteredNews(){

        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Body set
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();



        // Message
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

        String url = "http://192.168.0.47:9200/"+Indices.NEWS_CLUSTERED_INDEX;
        // Request
        HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

        return response.getBody();
    }


    // 조건에 맞는 모든 뉴스 가져오기
    public List<News> getNews(SearchNewsReqDTO dto){
        SearchRequest request = SearchUtil.buildNewsSearchRequest(Indices.NEWS_INDEX, dto);
        return searchInternal(request);
    }

    public List<News> getNews(SearchReqDTO dto){
        SearchRequest request = SearchUtil.buildSearchRequest(Indices.NEWS_INDEX, dto);
        return searchInternal(request);
    }

    // elasticsearch 로 부터 hit 가져오기
    private List<News> searchInternal(SearchRequest request) {
        if (request == null) {
            log.error("Failed to build search request");
            return Collections.emptyList();
        }

        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] searchHits = response.getHits().getHits();
            List<News> news = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                news.add(
                        MAPPER.readValue(hit.getSourceAsString(), News.class)
                );
            }

            return news;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
