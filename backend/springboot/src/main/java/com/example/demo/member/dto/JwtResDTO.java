package com.example.demo.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class JwtResDTO {
    private String token;
    private String type = "Bearer";
    private String email;
    private String role;
}
