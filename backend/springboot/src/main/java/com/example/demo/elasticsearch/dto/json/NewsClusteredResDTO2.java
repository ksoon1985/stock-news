package com.example.demo.elasticsearch.dto.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class NewsClusteredResDTO2 {

    @JsonProperty("hits")
    private Hits hits;

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

    // hits 추가
    public Hits getHits(){return hits;}
    public void setHits(Hits hits){this.hits = hits;}

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

    public static class Hits{
        @JsonProperty("total")
        private Total total;
        @JsonProperty("max_score")
        private long maxScore;
        @JsonProperty("hits")
        private List<InnerHits> innerHits;

        public Total getTotal() {
            return total;
        }

        public void setTotal(Total total) {
            this.total = total;
        }

        public long getMaxScore() {
            return maxScore;
        }

        public void setMaxScore(long maxScore) {
            this.maxScore = maxScore;
        }

        public List<InnerHits> getInnerHits(){return innerHits;}
        public void setInnerHits(List<InnerHits> innerHits){this.innerHits = innerHits;}
    }

    public static class Total{
        @JsonProperty("value")
        private int value;
        @JsonProperty("relation")
        private String relation;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getRelation() {
            return relation;
        }

        public void setRelation(String relation) {
            this.relation = relation;
        }
    }

    public static class InnerHits{
        @JsonProperty("_index")
        private String index;
        @JsonProperty("_type")
        private String type;
        @JsonProperty("_id")
        private String id;
        @JsonProperty("_score")
        private long score;
        @JsonProperty("_source")
        private Source source;

        @JsonProperty("highlight")
        private Highlight highlight;

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getScore() {
            return score;
        }

        public void setScore(long score) {
            this.score = score;
        }

        public Source getSource() {
            return source;
        }

        public void setSource(Source source) {
            this.source = source;
        }

        public Highlight getHighlight() {
            return highlight;
        }

        public void setHighlight(Highlight highlight) {
            this.highlight = highlight;
        }
    }

    public static class Source{
        @JsonProperty("registration_date")
        private String regDate;
        @JsonProperty("title")
        private String title;
        @JsonProperty("content")
        private String content;

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class Highlight{
        @JsonProperty("title")
        private List<String> title;

        public List<String> getTitle() {
            return title;
        }

        public void setTitle(List<String> title) {
            this.title = title;
        }
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
