package com.example.demo.elasticsearch.dto.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class NewsClusteredReqDTO {

    @JsonProperty("algorithm")
    private String algorithm;
    @JsonProperty("language")
    private String language;
    @JsonProperty("labelCount")
    private int labelcount;
    @JsonProperty("field_mapping")
    private FieldMapping fieldMapping;
    @JsonProperty("query_hint")
    private String queryHint;
    @JsonProperty("search_request")
    private SearchRequest searchRequest;

    @Getter @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldMapping {
        @JsonProperty("content")
        private List<String> content;
        @JsonProperty("title")
        private List<String> title;
    }

    @Getter @Setter
    public static class SearchRequest {
        @JsonProperty("size")
        private int size;
        @JsonProperty("query")
        private Query query;
        @JsonProperty("highlight")
        private Highlight highlight;
        @JsonProperty("_source")
        private List<String> source;
    }

    @Getter @Setter
    public static class Query {
        @JsonProperty("bool")
        private Bool bool;
    }
    @Getter @Setter
    public static class Bool {
        @JsonProperty("must")
        private List<Must> must;
    }

    @Getter @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Must {
        @JsonProperty("match")
        private Match match;

        @JsonProperty("range")
        private Range range;
    }

    @Getter @Setter
    @JsonNaming
    public static class Match {
        @JsonProperty("content")
        private QContent content;
    }

    @Getter @Setter
    public static class QContent {
        @JsonProperty("operator")
        private String operator;
        @JsonProperty("query")
        private String query;
    }

    @Getter @Setter
    @JsonNaming
    public static class Range{
        @JsonProperty("registration_date")
        private RegDate regDate;
    }

    @Getter @Setter
    public static class RegDate{
        @JsonProperty("gte")
        private String gte;
        @JsonProperty("lte")
        private String lte;
    }

    @Getter @Setter
    public static class Highlight {
        @JsonProperty("fields")
        private Fields fields;
        @JsonProperty("post_tags")
        private List<String> postTags;
        @JsonProperty("pre_tags")
        private List<String> preTags;

    }

    @Getter @Setter
    public static class Fields {
        @JsonProperty("title")
        private Title title;
        @JsonProperty("content")
        private Content content;
    }

    @Getter @Setter
    public static class Title {
        @JsonProperty("number_of_fragments")
        private int numberOfFragments;
        @JsonProperty("fragment_size")
        private int fragmentSize;

    }

    @Getter @Setter
    public static class Content {
        @JsonProperty("number_of_fragments")
        private int numberOfFragments;
        @JsonProperty("fragment_size")
        private int fragmentSize;

    }
}
