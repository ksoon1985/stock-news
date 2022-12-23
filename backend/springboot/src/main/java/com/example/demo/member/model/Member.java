package com.example.demo.member.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Document(collection = "member")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @ApiModelProperty(value = "_id",required = false,hidden = true)
    private String _id;

    // 이메일
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    // 닉네임
    @NotBlank
    @Size(max = 15)
    private String nickName;

    // 패스워드
    @NotBlank
    @Size(min = 5)
    private String password;

    // 권한
    @Field
    @ApiModelProperty(value = "권한",required = false,hidden = true)
    private Role role = Role.ROLE_MEMBER;

    // 성별
    private String gender;

    // 생년월일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthDay;

    // 사용 여부(사용 중 : Y , 삭제 시 : N)
    @Field
    @ApiModelProperty(value = "삭제 여부",required = false,hidden = true)
    private char useYn = 'Y';
}
