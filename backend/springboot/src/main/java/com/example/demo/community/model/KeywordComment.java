package com.example.demo.community.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document(collection="keyword_comment")
@Getter
@Setter
public class KeywordComment {
    @Id
    @ApiModelProperty(value = "_id",required = false,hidden = true)
    private String _id;

    // 대표 키워드
    @NotBlank
    private String keyword;

    // 커뮤니티 글 내용
    @NotBlank
    @Size(min = 5, max = 100)
    private String content;

    // 등록일
    @ApiModelProperty(value = "등록일",required = false,hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String regDate;

    // 대댓글 깊이
    @ApiModelProperty(required = false,hidden = true)
    private int depth;

    // 부모 댓글
    @ApiModelProperty(required = false,hidden = true)
    private String parentId = "";

    // 해당 댓글의 대댓글 수
    @ApiModelProperty(required = false,hidden = true)
    private int subCount;

    // 작성한 유저 이메일
    @ApiModelProperty(required = false,hidden = true)
    private String email;

    // 작성한 유저 닉네임
    @ApiModelProperty(required = false,hidden = true)
    private String nickName;
}
