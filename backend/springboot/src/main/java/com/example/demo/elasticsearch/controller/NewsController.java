package com.example.demo.elasticsearch.controller;

import com.example.demo.elasticsearch.dto.SearchNewsReqDTO;
import com.example.demo.elasticsearch.dto.json.NewsClusteredReqDTO;
import com.example.demo.elasticsearch.dto.json.NewsClusteredResDTO;
import com.example.demo.elasticsearch.model.News;
import com.example.demo.elasticsearch.service.NewsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
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
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

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
    public NewsClusteredResDTO getClusteredNews() throws JsonProcessingException {

        // news req dto
        NewsClusteredReqDTO newsDto = new NewsClusteredReqDTO();

         // search_request
        NewsClusteredReqDTO.SearchRequest searchRequest = new NewsClusteredReqDTO.SearchRequest();

          // _source
        ArrayList<String> sourceList = new ArrayList<>();
        sourceList.add("url");
        sourceList.add("title");
        sourceList.add("content");
        sourceList.add("registration_date");
        searchRequest.setSource(sourceList);

          // highlight
        NewsClusteredReqDTO.Highlight highlight = new NewsClusteredReqDTO.Highlight();
        ArrayList<String> preTagsList = new ArrayList<>();
        preTagsList.add("");
        preTagsList.add("");
        highlight.setPreTags(preTagsList);
        ArrayList<String> postTagsList = new ArrayList<>();
        postTagsList.add("");
        postTagsList.add("");
        highlight.setPostTags(postTagsList);
        NewsClusteredReqDTO.Fields fields = new NewsClusteredReqDTO.Fields();
        NewsClusteredReqDTO.Title title = new NewsClusteredReqDTO.Title();
        title.setFragmentSize(150);
        title.setNumberOfFragments(3);
        fields.setTitle(title);
        NewsClusteredReqDTO.Content content = new NewsClusteredReqDTO.Content();
        content.setFragmentSize(150);
        content.setNumberOfFragments(3);
        fields.setContent(content);
        highlight.setFields(fields);
        searchRequest.setHighlight(highlight);

          // query
        NewsClusteredReqDTO.Match match = new NewsClusteredReqDTO.Match();
        match.setContent("삼성전자");
        NewsClusteredReqDTO.Query query = new NewsClusteredReqDTO.Query();
        query.setMatch(match);
        searchRequest.setQuery(query);

          // size
        searchRequest.setSize(100);
        newsDto.setSearchRequest(searchRequest);

         // query_hint
        newsDto.setQueryHint("");

         // field_mapping
        NewsClusteredReqDTO.FieldMapping fieldMapping = new NewsClusteredReqDTO.FieldMapping();
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("_source.title");
        fieldMapping.setTitle(titleList);
        ArrayList<String> contentList = new ArrayList<>();
        contentList.add("highlight.content");
        fieldMapping.setContent(contentList);
        newsDto.setFieldMapping(fieldMapping);

         // language
        newsDto.setLanguage("Korean");

         // algorithm
        newsDto.setAlgorithm("Lingo");

        // es 에 요청 ================================================================================

        // dto -> json String
        String jsonString = objectMapper.writeValueAsString(newsDto);

        System.out.println("########################"+jsonString);

        // Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Message
        HttpEntity<?> requestMessage = new HttpEntity<>(newsDto, httpHeaders);

        String url = "http://192.168.0.47:9200/naver.news/_search_with_clusters?pretty=true";
        // Request
        HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);


        // es 응답 json string -> java object 파싱 ===================================================
        NewsClusteredResDTO resDto = objectMapper.readValue(response.getBody(), NewsClusteredResDTO.class);

        return resDto;
    }
}
