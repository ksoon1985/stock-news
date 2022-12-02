package com.example.demo.community.service;

import com.example.demo.community.model.Comment;
import com.example.demo.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;

    private final MongoTemplate mongoTemplate;

    /**
     * 글 작성
     */
    public Comment insertComment(Comment comment){

        return communityRepository.insert(comment);
    }

    /**
    * 글 조회
     * 종목에 맞는 커뮤니티 글 조회
     */
    public List<Comment> getCommentsByStockCode(String stockCode){

        return communityRepository.findByCodeOrderByRegDateDesc(stockCode);
    }


    /**
     * 글 삭제
     * 종목 고유 id
     */
    public void delComment(String id){
        if(communityRepository.existsById(id)){
            communityRepository.deleteById(id);
        }
    }


}
