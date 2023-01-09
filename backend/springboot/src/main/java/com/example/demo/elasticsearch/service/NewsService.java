package com.example.demo.elasticsearch.service;

import com.example.demo.elasticsearch.dto.ClusteredNews;
import com.example.demo.elasticsearch.dto.CustomBucket;
import com.example.demo.elasticsearch.dto.SearchNewsReqDTO;
import com.example.demo.elasticsearch.dto.json.NewsClusteredReqDTO;
import com.example.demo.elasticsearch.dto.json.NewsClusteredResDTO;
import com.example.demo.elasticsearch.dto.json.NewsClusteredResDTO2;
import com.example.demo.elasticsearch.model.News;
import com.example.demo.elasticsearch.repository.NewsSearchRepository;
import com.example.demo.elasticsearch.utils.Indices;
import com.example.demo.elasticsearch.utils.SearchUtil;
import com.example.demo.stock.service.StockService;
import com.example.demo.util.CustomDateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.elasticsearch.search.aggregations.bucket.terms.SignificantTerms;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewsService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private final StockService stockService;
    private final RestHighLevelClient client;
    private final NewsSearchRepository newsSearchRepository;

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private String port;

    @Value("${elasticsearch.username}")
    private String username;

    @Value("${elasticsearch.password}")
    private String password;

    // elasticsearch news id 로 news 정보 받아오기
    public News getNewsById(String newsId) {
        try {
            GetResponse documentFields = client.get(new GetRequest(Indices.NEWS_INDEX, newsId), RequestOptions.DEFAULT);

            if (documentFields == null || documentFields.isSourceEmpty()) {
                return null;
            }

            // json 형태를 객체 형태로 반환
            News news = objectMapper.readValue(documentFields.getSourceAsString(), News.class);
            // utc date 처리
            news.setRegistration_date(CustomDateUtil.UTCToCustomDateTime(news.getRegistration_date()));
            return news;
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    // 종목,대표 키워드, 기간에 맞는 모든 뉴스 가져오기
    public List<News> getNews(SearchNewsReqDTO dto){

        String stockName = stockService.getStockNameByStockCode(dto.getSearchTerm());
        dto.setSearchTerm(stockName);

        SearchRequest request = SearchUtil.buildNewsSearchRequest(Indices.NEWS_INDEX, dto);
        return searchInternal(request);
    }

    // 종목에 맞는 최신 뉴스 가져오기
    // 조건 추가 - category_id 별로
    public List<News> getRealTimeNews(SearchNewsReqDTO dto){
        String stockName = stockService.getStockNameByStockCode(dto.getSearchTerm());
        dto.setSearchTerm(stockName);

        SearchRequest request = SearchUtil.buildNewsSearchRequestStockNameAndCategoryId(Indices.NEWS_INDEX, dto);
        return searchInternal(request);
    }

    // 대표 키워드 가져오기
    public ArrayList<CustomBucket> getTopicKeywords(SearchNewsReqDTO dto){

        String stockName = stockService.getStockNameByStockCode(dto.getSearchTerm());
        dto.setSearchTerm(stockName);

        ArrayList<CustomBucket> bucketList = new ArrayList<>();

        SearchRequest request = SearchUtil.buildKeywordSearchRequest(Indices.NEWS_INDEX, dto);
        try{
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SignificantTerms significantTerms = response.getAggregations().get("agg_content");

            for (SignificantTerms.Bucket bucket : significantTerms.getBuckets()) {

                CustomBucket customBucket = new CustomBucket();
                customBucket.setKeyword(bucket.getKeyAsString());
                customBucket.setScore(bucket.getSignificanceScore());
                customBucket.setDocCount(bucket.getDocCount());
                customBucket.setBgCount(bucket.getSupersetDf());
                bucketList.add(customBucket);
            }

            return bucketList;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return bucketList;
        }
    }

    // 대표키워드 한달전 데이터와 비교
    public ArrayList<CustomBucket> topicKeywordsCompare1Month(SearchNewsReqDTO dto){

        String stockCode = dto.getSearchTerm();

        Calendar cal = Calendar.getInstance();

        // fromDate
        String fromDateStr = dto.getFromDate();
        Date fromDate = CustomDateUtil.stringToDate(fromDateStr);
        cal.setTime(fromDate);

        // fromDate 에서 하루전
        cal.add(Calendar.DATE,-1);
        Date fromDateBefore1Day = cal.getTime();
        // fromDate 에서 하루전에서 한달전
        cal.add(Calendar.MONTH,-1);
        Date fromDateBefore1Month = cal.getTime();

        // 사용자가 지정한 기간의 토픽 키워드
        ArrayList<CustomBucket> nowBucketList = getTopicKeywords(dto);

        dto.setSearchTerm(stockCode);
        dto.setFromDate(CustomDateUtil.dateToString(fromDateBefore1Month));
        dto.setToDate(CustomDateUtil.dateToString(fromDateBefore1Day));

        // 사용자가 지정한 기간의 한달전 토픽 키워드
        ArrayList<CustomBucket> beforeBucketList = getTopicKeywords(dto);

        for(int i=0;i<nowBucketList.size();i++){

            CustomBucket nowBucket = nowBucketList.get(i);
            nowBucket.setNew(true);
            String nowKeyword = nowBucket.getKeyword();

            for(int j=0;j<beforeBucketList.size();j++){
                CustomBucket beforeBucket = beforeBucketList.get(j);
                String beforeKeyword = beforeBucket.getKeyword();

                // 일치하는 키워드가 있다면
                if(nowKeyword.equals(beforeKeyword)){
                    nowBucket.setRank(j-i);
                    nowBucket.setNew(false);
                    continue;
                }
            }

            nowBucketList.set(i,nowBucket);
        }

        return nowBucketList;
    }


    // 종목, 기간에 일치하는 클러스터링 된 뉴스 가져오기
    public  ArrayList<ClusteredNews> getClusteredNews1(SearchNewsReqDTO newsReqDTO) throws JsonProcessingException {

        String stockName = stockService.getStockNameByStockCode(newsReqDTO.getSearchTerm());
        newsReqDTO.setSearchTerm(stockName);

        // 엘라스틱 서치에 요청할 carrot2 전용 json dto 생성 ====================================================
        NewsClusteredReqDTO newsDto = SearchUtil.buildRequestJsonQuery(newsReqDTO);

        System.out.println("########################"+objectMapper.writeValueAsString(newsDto).toString());

        // HTTP Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth("ZWxhc3RpYzoxMjM0NTY=");

        // HTTP Message set
        HttpEntity<?> requestMessage = new HttpEntity<>(newsDto, httpHeaders);

        // 엘라스틱 서치 요청 url
        //String url = "http://"+username+":"+password+"@"+host+":"+port+"/"+Indices.NEWS_CLUSTERED_INDEX ;
        String url = "http://"+host+":"+port+"/"+Indices.NEWS_CLUSTERED_INDEX ;
        //String url = "https://f37c-221-148-195-245.jp.ngrok.io"+"/"+Indices.NEWS_CLUSTERED_INDEX ;
        ArrayList<ClusteredNews> clusteredNewsList = new ArrayList<>();

        // 요청 실행 !!!
        try{
            HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

            // 엘라스틱서치에서 준 응답 데이터
            // json String -> json dto 로 변환 ===================================================================
            NewsClusteredResDTO resDto = objectMapper.readValue(response.getBody(), NewsClusteredResDTO.class);

            // json dto 를 가지고 프론트에 응답할 뉴스 데이터 작업
            List<NewsClusteredResDTO.Clusters> clusters = resDto.getClusters();

            //int clustersSize = clusters.size();
            // 첫번째 클러스터는 성능이 안좋아서 제외, 단 size 가 1개일 땐 그냥 출력
//            boolean isFirst = true;
//
//            if(clustersSize > 1){
//                isFirst = true;
//            }else{
//                isFirst = false;
//            }

            // 클러스터링된 뉴스 결과 리스트 - 토픽 뉴스 버전
            for (NewsClusteredResDTO.Clusters cluster : clusters) {
//                if( isFirst){
//                    isFirst = false;
//                    continue;
//                }

                ClusteredNews clusteredNews = new ClusteredNews();

                clusteredNews.setScore(cluster.getScore());
                clusteredNews.setLabel(cluster.getLabel());
                clusteredNews.setPhrases(cluster.getPhrases());

                // 군집화된 뉴스중에서 첫번째를 토픽뉴스로 선정
                String documentId = cluster.getDocuments().get(0);

                // 군집화된(중복된) 뉴스 갯수
                clusteredNews.setCount(cluster.getDocuments().size());

                News news = getNewsById(documentId);

                if(news != null){
                    clusteredNews.setNews(news);
                    //clusteredNews.setTopicNews(news);
                }

                clusteredNewsList.add(clusteredNews);
            }

        }catch (Exception e){
            System.out.println(e);
            return clusteredNewsList;
        }


        // 클러스터링된 뉴스 결과 리스트 - 헤드라인 뉴스 버전
        /*
        for (NewsClusteredResDTO.Clusters cluster : clusters) {

            ClusteredNews clusteredNews = new ClusteredNews();
            ArrayList<News> newsList = new ArrayList<>();

            clusteredNews.setScore(cluster.getScore());
            clusteredNews.setLabel(cluster.getLabel());

            cluster.getDocuments().forEach(documentId->{
                News news = getNewsById(documentId);
                if(news != null){
                    newsList.add(news);
                }
            });
            clusteredNews.setNews(newsList);

            clusteredNewsList.add(clusteredNews);
        }
         */

        return clusteredNewsList;
    }


    // 종목, 기간에 일치하는 클러스터링 된 뉴스 가져오기2
    public  ArrayList<ClusteredNews> getClusteredNews2(SearchNewsReqDTO newsReqDTO) throws JsonProcessingException {

        String stockName = stockService.getStockNameByStockCode(newsReqDTO.getSearchTerm());
        newsReqDTO.setSearchTerm(stockName);

        // 엘라스틱 서치에 요청할 carrot2 전용 json dto 생성 ====================================================
        NewsClusteredReqDTO newsDto = SearchUtil.buildRequestJsonQuery(newsReqDTO);

        System.out.println("########################"+objectMapper.writeValueAsString(newsDto).toString());

        // HTTP Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth("ZWxhc3RpYzoxMjM0NTY=");

        // HTTP Message set
        HttpEntity<?> requestMessage = new HttpEntity<>(newsDto, httpHeaders);

        // 엘라스틱 서치 요청 url
        //String url = "http://"+username+":"+password+"@"+host+":"+port+"/"+Indices.NEWS_CLUSTERED_INDEX ;
        String url = "http://"+host+":"+port+"/"+Indices.NEWS_CLUSTERED_INDEX ;
        //String url = "https://f37c-221-148-195-245.jp.ngrok.io"+"/"+Indices.NEWS_CLUSTERED_INDEX ;

        // 반환할 군집화된 뉴스 리스트
        ArrayList<ClusteredNews> clusteredNewsList = new ArrayList<>();

        // 요청 실행 !!!
        try{
            HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

            // 엘라스틱서치에서 준 응답 데이터
            // json String -> json dto 로 변환 ===================================================================
            NewsClusteredResDTO2 resDto = objectMapper.readValue(response.getBody(), NewsClusteredResDTO2.class);

            NewsClusteredResDTO2.Hits hits = resDto.getHits();
            List<NewsClusteredResDTO2.InnerHits> innerHits = hits.getInnerHits();

            HashMap<String, NewsClusteredResDTO2.Source> mapNews = new HashMap<>();

            for (NewsClusteredResDTO2.InnerHits innerHit : innerHits) {
                mapNews.put(innerHit.getId(),innerHit.getSource());
            }

            // json dto 를 가지고 프론트에 응답할 뉴스 데이터 작업
            List<NewsClusteredResDTO2.Clusters> clusters = resDto.getClusters();

            // 클러스터링된 뉴스 결과 리스트
            for (NewsClusteredResDTO2.Clusters cluster : clusters) {

                ClusteredNews clusteredNews = new ClusteredNews();

                clusteredNews.setScore(cluster.getScore());
                clusteredNews.setLabel(cluster.getLabel());
                clusteredNews.setPhrases(cluster.getPhrases());

                // 각 군집화된 뉴스 개수
                int clusterSize = cluster.getDocuments().size();

                ArrayList<News> newsList = new ArrayList<>();

                String documentId = null;
                for(int i=0;i<clusterSize;i++){

                    documentId = cluster.getDocuments().get(i);

                    NewsClusteredResDTO2.Source source = mapNews.get(documentId);

                    if(source != null){
                        News news = new News();
                        news.setTitle(mapNews.get(documentId).getTitle());
                        news.setContent(mapNews.get(documentId).getContent());
                        news.setRegistration_date(mapNews.get(documentId).getRegDate());

                        if(i==0){
                            clusteredNews.setNews(news);
                        }
                        newsList.add(news);
                    }

                }
                clusteredNews.setNewsList(newsList);

                // 군집화된 뉴스 갯수 set
                clusteredNews.setCount(clusterSize);

                clusteredNewsList.add(clusteredNews);
            }

        }catch (Exception e){
            System.out.println(e);
            return clusteredNewsList;
        }

        return clusteredNewsList;
    }


    // 종목, 기간에 일치하는 클러스터링 된 뉴스 가져오기3
    public  ArrayList<ClusteredNews> getClusteredNews3(SearchNewsReqDTO newsReqDTO) throws JsonProcessingException {

        String stockName = stockService.getStockNameByStockCode(newsReqDTO.getSearchTerm());
        newsReqDTO.setSearchTerm(stockName);

        // 엘라스틱 서치에 요청할 carrot2 전용 json dto 생성 ====================================================
        NewsClusteredReqDTO newsDto = SearchUtil.buildRequestJsonQuery(newsReqDTO);

        System.out.println("########################"+objectMapper.writeValueAsString(newsDto).toString());

        // HTTP Header set
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth("ZWxhc3RpYzoxMjM0NTY=");

        // HTTP Message set
        HttpEntity<?> requestMessage = new HttpEntity<>(newsDto, httpHeaders);

        // 엘라스틱 서치 요청 url
        //String url = "http://"+username+":"+password+"@"+host+":"+port+"/"+Indices.NEWS_CLUSTERED_INDEX ;
        String url = "http://"+host+":"+port+"/"+Indices.NEWS_CLUSTERED_INDEX ;
        //String url = "https://f37c-221-148-195-245.jp.ngrok.io"+"/"+Indices.NEWS_CLUSTERED_INDEX ;

        // 반환할 군집화된 뉴스 리스트
        ArrayList<ClusteredNews> clusteredNewsList = new ArrayList<>();

        // 요청 실행 !!!
        try{
            HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

            // 엘라스틱서치에서 준 응답 데이터
            // json String -> json dto 로 변환 ===================================================================
            NewsClusteredResDTO resDto = objectMapper.readValue(response.getBody(), NewsClusteredResDTO.class);

            // json dto 를 가지고 프론트에 응답할 뉴스 데이터 작업
            List<NewsClusteredResDTO.Clusters> clusters = resDto.getClusters();

            // 클러스터링된 뉴스 결과 리스트
            for (NewsClusteredResDTO.Clusters cluster : clusters) {

                ClusteredNews clusteredNews = new ClusteredNews();

                clusteredNews.setScore(cluster.getScore());
                clusteredNews.setLabel(cluster.getLabel());
                clusteredNews.setPhrases(cluster.getPhrases());

                // 각 군집화된 뉴스 개수
                int clusterSize = cluster.getDocuments().size();

                ArrayList<News> newsList = new ArrayList<>();

                String documentId = null;
                for(int i=0;i<clusterSize;i++){

                    documentId = cluster.getDocuments().get(i);

                    News news = getNewsById(documentId);

                    if(news != null){
                        // 첫번째 뉴스는 대표뉴스로 선정
                        if(i ==0 ){
                            clusteredNews.setNews(news);
                        }else{
                            newsList.add(news);
                        }
                    }
                }
                clusteredNews.setNewsList(newsList);

                // 군집화된 뉴스 갯수 set
                clusteredNews.setCount(clusterSize);

                clusteredNewsList.add(clusteredNews);
            }

        }catch (Exception e){
            System.out.println(e);
            return clusteredNewsList;
        }

        return clusteredNewsList;
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

                News newsTmp = objectMapper.readValue(hit.getSourceAsString(), News.class);
                newsTmp.setRegistration_date(CustomDateUtil.UTCToCustomDateTime(newsTmp.getRegistration_date()));

                news.add(newsTmp);
            }

            return news;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

}
