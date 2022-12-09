package com.example.demo.elasticsearch.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLabelcount() {
        return labelcount;
    }

    public void setLabelcount(int labelcount) {
        this.labelcount = labelcount;
    }

    public FieldMapping getFieldMapping() {
        return fieldMapping;
    }

    public void setFieldMapping(FieldMapping fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    public String getQueryHint() {
        return queryHint;
    }

    public void setQueryHint(String queryHint) {
        this.queryHint = queryHint;
    }

    public SearchRequest getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(SearchRequest searchRequest) {
        this.searchRequest = searchRequest;
    }

    public static class FieldMapping {
        @JsonProperty("content")
        private List<String> content;
        @JsonProperty("title")
        private List<String> title;

        public List<String> getContent() {
            return content;
        }

        public void setContent(List<String> content) {
            this.content = content;
        }

        public List<String> getTitle() {
            return title;
        }

        public void setTitle(List<String> title) {
            this.title = title;
        }
    }

    public static class SearchRequest {
        @JsonProperty("size")
        private int size;
        @JsonProperty("query")
        private Query query;
        @JsonProperty("highlight")
        private Highlight highlight;
        @JsonProperty("_source")
        private List<String> source;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public Query getQuery() {
            return query;
        }

        public void setQuery(Query query) {
            this.query = query;
        }

        public Highlight getHighlight() {
            return highlight;
        }

        public void setHighlight(Highlight highlight) {
            this.highlight = highlight;
        }

        public List<String> getSource() {
            return source;
        }

        public void setSource(List<String> source) {
            this.source = source;
        }
    }

    public static class Query {
        @JsonProperty("match")
        private Match match;

        public Match getMatch() {
            return match;
        }

        public void setMatch(Match match) {
            this.match = match;
        }
    }

    public static class Match {
        @JsonProperty("content")
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class Highlight {
        @JsonProperty("fields")
        private Fields fields;
        @JsonProperty("post_tags")
        private List<String> postTags;
        @JsonProperty("pre_tags")
        private List<String> preTags;

        public Fields getFields() {
            return fields;
        }

        public void setFields(Fields fields) {
            this.fields = fields;
        }

        public List<String> getPostTags() {
            return postTags;
        }

        public void setPostTags(List<String> postTags) {
            this.postTags = postTags;
        }

        public List<String> getPreTags() {
            return preTags;
        }

        public void setPreTags(List<String> preTags) {
            this.preTags = preTags;
        }
    }

    public static class Fields {
        @JsonProperty("title")
        private Title title;
        @JsonProperty("content")
        private Content content;

        public Title getTitle() {
            return title;
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }
    }

    public static class Title {
        @JsonProperty("number_of_fragments")
        private int numberOfFragments;
        @JsonProperty("fragment_size")
        private int fragmentSize;

        public int getNumberOfFragments() {
            return numberOfFragments;
        }

        public void setNumberOfFragments(int numberOfFragments) {
            this.numberOfFragments = numberOfFragments;
        }

        public int getFragmentSize() {
            return fragmentSize;
        }

        public void setFragmentSize(int fragmentSize) {
            this.fragmentSize = fragmentSize;
        }
    }

    public static class Content {
        @JsonProperty("number_of_fragments")
        private int numberOfFragments;
        @JsonProperty("fragment_size")
        private int fragmentSize;

        public int getNumberOfFragments() {
            return numberOfFragments;
        }

        public void setNumberOfFragments(int numberOfFragments) {
            this.numberOfFragments = numberOfFragments;
        }

        public int getFragmentSize() {
            return fragmentSize;
        }

        public void setFragmentSize(int fragmentSize) {
            this.fragmentSize = fragmentSize;
        }
    }
}
