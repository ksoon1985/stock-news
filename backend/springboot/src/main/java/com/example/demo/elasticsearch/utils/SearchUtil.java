package com.example.demo.elasticsearch.utils;

import com.example.demo.elasticsearch.dto.SearchNewsReqDTO;
import com.example.demo.elasticsearch.dto.SearchReqDTO;
import com.example.demo.elasticsearch.dto.json.NewsClusteredReqDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchUtil {

    // news search query builder
    public static SearchRequest buildNewsSearchRequest(String indexName, SearchNewsReqDTO dto){

        try {
            // query
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery("title", dto.getSearchTerm()))// 삼성 전자
                    .must(QueryBuilders.matchPhraseQuery("content",dto.getThemeKeyword()))
                    .must(QueryBuilders.rangeQuery("registration_date").gte(dto.getFromDate()).lte(dto.getToDate()));

            SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(boolQueryBuilder);

            // sorting
            builder = builder.sort("registration_date",SortOrder.DESC);

            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;
        }catch (Exception e){
            return null;
        }
    }

    // 뉴스 클러스터링 요청 json(dto) 만드는 기능 (custom)
    public static NewsClusteredReqDTO buildRequestJsonQuery(SearchNewsReqDTO newsReqDTO){

        String searchTerm = newsReqDTO.getSearchTerm();
        String fromDate = newsReqDTO.getFromDate();
        String toDate = newsReqDTO.getToDate();

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
        NewsClusteredReqDTO.Query query = new NewsClusteredReqDTO.Query();
        NewsClusteredReqDTO.Bool bool = new NewsClusteredReqDTO.Bool();

        NewsClusteredReqDTO.Must must1 = new NewsClusteredReqDTO.Must();
        NewsClusteredReqDTO.Match match = new NewsClusteredReqDTO.Match();
        NewsClusteredReqDTO.QContent qContent = new NewsClusteredReqDTO.QContent();
        qContent.setQuery(searchTerm);
        qContent.setOperator("and");
        match.setContent(qContent);
        must1.setMatch(match);

        NewsClusteredReqDTO.Must must2 = new NewsClusteredReqDTO.Must();
        NewsClusteredReqDTO.Range range = new NewsClusteredReqDTO.Range();
        NewsClusteredReqDTO.RegDate regDate = new NewsClusteredReqDTO.RegDate();
        regDate.setGte(fromDate);
        regDate.setLte(toDate);
        range.setRegDate(regDate);
        must2.setRange(range);
        ArrayList<NewsClusteredReqDTO.Must> musts = new ArrayList<>();
        musts.add(must1);
        musts.add(must2);
        bool.setMust(musts);
        query.setBool(bool);
        searchRequest.setQuery(query);

        // size
        searchRequest.setSize(1000);
        newsDto.setSearchRequest(searchRequest);

        // query_hint
        newsDto.setQueryHint("");

        // field_mapping
        NewsClusteredReqDTO.FieldMapping fieldMapping = new NewsClusteredReqDTO.FieldMapping();
        ArrayList<String> titleList = new ArrayList<>();
        titleList.add("_source.title");
        fieldMapping.setTitle(titleList);
//        ArrayList<String> contentList = new ArrayList<>();
//        contentList.add("highlight.content");
//        fieldMapping.setContent(contentList);
        newsDto.setFieldMapping(fieldMapping);

        // language
        newsDto.setLanguage("English");

        // algorithm
        newsDto.setAlgorithm("Bisecting K-Means");

        return newsDto;
    }



    // 임시 참고용 ================================================================================

    // queryBuilder -> searchSourceBuilder -> searchRequest 로 검색 조건 만듦
    // indexName, dto
    public static SearchRequest buildSearchRequest(String indexName, SearchReqDTO dto){

        try{

            // paging
            final int page = dto.getPage();
            final int size = dto.getSize();
            final int from = page <= 0 ? 0 : page * size;

            SearchSourceBuilder builder = new SearchSourceBuilder()
                    .from(from)
                    .size(size)
                    .postFilter(getQueryBuilder(dto));

            // sorting
            if(dto.getSortBy() != null){
                builder = builder.sort(
                        dto.getSortBy(),
                        dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
            }

            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    // queryBuilder -> searchSourceBuilder -> searchRequest 로 검색 조건 만듦
    // indexName, field, date
    public static SearchRequest buildSearchRequest(String indexName, String field, Date date){
        try {

            SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getQueryBuilder(field, date));
            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // queryBuilder -> BoolQueryBuilder -> searchSourceBuilder -> searchRequest 로 검색 조건 만듦
    // indexName, dto, date
    public static SearchRequest buildSearchRequest(String indexName, SearchReqDTO dto, Date date) {
        try {
            QueryBuilder searchQuery = getQueryBuilder(dto);

            QueryBuilder dateQuery = getQueryBuilder("created", date);

            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                    .must(searchQuery)
                    .must(dateQuery);

            SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(boolQuery);

            if (dto.getSortBy() != null) {
                builder = builder.sort(
                        dto.getSortBy(),
                        dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC
                );
            }

            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // search 를 실행할 queryBuilder 가져오기 (검색할 field 와 searchTerm 을 셋팅시켜서 반환)
    // field : 검색 할 index 의 field
    // searchTerm : 검색 term
    public static QueryBuilder getQueryBuilder(SearchReqDTO dto){
        if(dto == null){
            return null;
        }

        List<String> fields = dto.getFields();
        if(CollectionUtils.isEmpty(fields)){
            return null;
        }

        // multi query 일 때
        if(fields.size() > 1){
            // MultiMatchQuery : 여러 field 를 통해 검색
            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm())
                    .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
                    .operator(Operator.AND);

            // queryBuilder 에 field add
            fields.forEach(queryBuilder::field);

            return queryBuilder;
        }

        // 단일 쿼리 일 때
        return fields.stream()
                .findFirst()
                .map(field -> QueryBuilders.matchQuery(field, dto.getSearchTerm())
                        .operator(Operator.AND))
                .orElse(null);
    }

    //  created >= date 인 경우
    public static QueryBuilder getQueryBuilder(String field , Date date){
        return QueryBuilders.rangeQuery(field).gte(date);
    }
}
