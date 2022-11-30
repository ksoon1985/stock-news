package com.example.demo.elasticsearch.utils;

import com.example.demo.elasticsearch.dto.SearchNewsReqDTO;
import com.example.demo.elasticsearch.dto.SearchReqDTO;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

public class SearchUtil {

    // news search query builder
    public static SearchRequest buildNewsSearchRequest(String indexName, SearchNewsReqDTO dto){

        try {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                    .must(QueryBuilders.matchQuery("content", dto.getSearchTerm()))//ex 삼성전자
                    .must(QueryBuilders.rangeQuery("registration_date").gte(dto.getFromDate()).lte(dto.getToDate()));

            SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(boolQueryBuilder);

            SearchRequest request = new SearchRequest(indexName);
            request.source(builder);

            return request;
        }catch (Exception e){
            return null;
        }
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

    // board created >= date 인 경우
    public static QueryBuilder getQueryBuilder(String field , Date date){
        return QueryBuilders.rangeQuery(field).gte(date);
    }
}
