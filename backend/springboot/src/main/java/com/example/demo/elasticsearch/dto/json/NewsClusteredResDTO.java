package com.example.demo.elasticsearch.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsClusteredResDTO {

    @JsonProperty("info")
    private Info info;
    @JsonProperty("clusters")
    private List<Clusters> clusters;
    @JsonProperty("_shards")
    private Shards shards;
    @JsonProperty("timed_out")
    private boolean timedOut;
    @JsonProperty("took")
    private int took;

    @Getter @Setter
    public static class Info {
        @JsonProperty("languages")
        private String languages;
        @JsonProperty("max-hits")
        private String maxHits;
        @JsonProperty("total-millis")
        private String totalMillis;
        @JsonProperty("clustering-millis")
        private String clusteringMillis;
        @JsonProperty("search-millis")
        private String searchMillis;
        @JsonProperty("algorithm")
        private String algorithm;

    }

    @Getter @Setter
    public static class Clusters {
        @JsonProperty("documents")
        private List<String> documents;
        @JsonProperty("phrases")
        private List<String> phrases;
        @JsonProperty("label")
        private String label;
        @JsonProperty("score")
        private double score;
        @JsonProperty("id")
        private int id;

    }

    @Getter @Setter
    public static class Shards {
        @JsonProperty("failed")
        private int failed;
        @JsonProperty("skipped")
        private int skipped;
        @JsonProperty("successful")
        private int successful;
        @JsonProperty("total")
        private int total;

    }
}
