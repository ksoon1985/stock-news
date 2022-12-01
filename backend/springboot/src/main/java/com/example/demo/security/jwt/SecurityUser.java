package com.example.demo.security.jwt;

import com.example.demo.member.model.Member;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {

    private Member member;

    public SecurityUser(Member member){
        super(member.getEmail(),member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().getRoleName()));
        this.member = member;
    }

    public Member getMember() {
        return this.member;
    }
}
