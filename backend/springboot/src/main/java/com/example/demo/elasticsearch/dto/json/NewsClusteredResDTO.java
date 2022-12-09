package com.example.demo.elasticsearch.dto.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

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

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Clusters> getClusters() {
        return clusters;
    }

    public void setClusters(List<Clusters> clusters) {
        this.clusters = clusters;
    }

    public Shards getShards() {
        return shards;
    }

    public void setShards(Shards shards) {
        this.shards = shards;
    }

    public boolean getTimedOut() {
        return timedOut;
    }

    public void setTimedOut(boolean timedOut) {
        this.timedOut = timedOut;
    }

    public int getTook() {
        return took;
    }

    public void setTook(int took) {
        this.took = took;
    }

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

        public String getLanguages() {
            return languages;
        }

        public void setLanguages(String languages) {
            this.languages = languages;
        }

        public String getMaxHits() {
            return maxHits;
        }

        public void setMaxHits(String maxHits) {
            this.maxHits = maxHits;
        }

        public String getTotalMillis() {
            return totalMillis;
        }

        public void setTotalMillis(String totalMillis) {
            this.totalMillis = totalMillis;
        }

        public String getClusteringMillis() {
            return clusteringMillis;
        }

        public void setClusteringMillis(String clusteringMillis) {
            this.clusteringMillis = clusteringMillis;
        }

        public String getSearchMillis() {
            return searchMillis;
        }

        public void setSearchMillis(String searchMillis) {
            this.searchMillis = searchMillis;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }
    }

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

        public List<String> getDocuments() {
            return documents;
        }

        public void setDocuments(List<String> documents) {
            this.documents = documents;
        }

        public List<String> getPhrases() {
            return phrases;
        }

        public void setPhrases(List<String> phrases) {
            this.phrases = phrases;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class Shards {
        @JsonProperty("failed")
        private int failed;
        @JsonProperty("skipped")
        private int skipped;
        @JsonProperty("successful")
        private int successful;
        @JsonProperty("total")
        private int total;

        public int getFailed() {
            return failed;
        }

        public void setFailed(int failed) {
            this.failed = failed;
        }

        public int getSkipped() {
            return skipped;
        }

        public void setSkipped(int skipped) {
            this.skipped = skipped;
        }

        public int getSuccessful() {
            return successful;
        }

        public void setSuccessful(int successful) {
            this.successful = successful;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
