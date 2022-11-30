package com.example.demo.community.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection="community")
@Getter
@Setter
public class Community {
    @Id
    private String _id;
    // 주식 종목 코드
    @NotBlank
    private String code;
    // 유저 이메일
    @NotBlank
    private String email;
    // 커뮤니티 글 내용
    @NotBlank
    private String content;
    //

    // 등록일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;
}
