package com.example.demo.member.service;

import com.example.demo.member.model.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MongoTemplate mongoTemplate;

    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 처리
     */
    public Member signUpMember(Member member){
        System.out.println("회원가입 서비스 호출 ");
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        return  memberRepository.insert(member);
    }

    /**
     * 회원가입 시
     * email 중복 체크
     *
     */
    public Boolean emailChk(String email){
        Boolean find = true;
        Member member = memberRepository.findByEmail(email);
        if(member != null){
            find = false;
        }
        return find;
    }

    /**
     * 회원가입 시
     * nickName 중복 체크
     */
    public Boolean nickNameChk(String nickName){
        Boolean find = true;
        Member member = memberRepository.findByNickName(nickName);
        if(member != null){
            find = false;
        }
        return find;
    }

}
