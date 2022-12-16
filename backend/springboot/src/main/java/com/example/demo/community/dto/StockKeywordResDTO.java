package com.example.demo.community.dto;

import com.example.demo.community.model.KeywordComment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StockKeywordResDTO {
    String keyword;
    int likeCount;
    int commentCount;
    List<KeywordComment> keywordComments;
}
