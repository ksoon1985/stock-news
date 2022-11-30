package com.example.demo.member.repository;

import com.example.demo.member.model.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberRepository extends MongoRepository<Member,String> {
    public Member findByEmail(String email);
    public Member findByNickName(String nickName);

}
