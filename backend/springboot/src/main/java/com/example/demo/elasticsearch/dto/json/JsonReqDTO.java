package com.example.demo.elasticsearch.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

// 임시
@Getter @Setter
public class JsonReqDTO {

    @JsonProperty(value="search_request")
    private JsonSearchRequest searchRequest;
    @JsonProperty(value = "query_hint")
    private String queryHint;
    private String algorithm;
    @JsonProperty(value = "field_mapping")
    private JsonFieldMapping fieldMapping;


    @Getter @Setter
    public class JsonSearchRequest{

        @JsonProperty(value = "_source")
        ArrayList<String> source;

    }

    @Getter @Setter
    public class JsonFieldMapping{

        ArrayList<String> title;
        ArrayList<String> content;
    }
}


