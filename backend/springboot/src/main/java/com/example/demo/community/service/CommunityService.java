package com.example.demo.community.service;

import com.example.demo.community.model.Comment;
import com.example.demo.community.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
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
     * 대댓글 등록 요청시
     * 부모 댓글 subCount 증가
     */
    public void increaseSubCount(String parentId){
        Comment comment = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(parentId)), Comment.class);
        if(comment != null){
            comment.setSubCount(comment.getSubCount() + 1);
            mongoTemplate.save(comment,"comment");
        }
    }

    /**
    * 글 조회
     * 종목에 맞는 커뮤니티 글 조회
     * 먼저 최 상단 댓글 (대댓글 x) 목록 출력
     */
    public List<Comment> getCommentsByStockCode(String stockCode){

        return communityRepository.findByCodeAndParentIdOrderByRegDateAsc(stockCode,"");
    }

    /**
     * 글 조회
     * 종목에 맞는 커뮤니티 글 조회
     * 먼저 최 상단 댓글 (대댓글 x) 목록 출력
     */
    public List<Comment> getCommentsByParentId(String commentId){

        return communityRepository.findByParentIdOrderByRegDateAsc(commentId);
    }


    /**
     * 글 삭제
     * 종목 고유 id
     */
    public void delComment(String id){
        if(communityRepository.existsById(id)){
            Comment comment = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), Comment.class);
            // 대댓글일때 부모 댓글 subCount 감소
            if(!comment.getParentId().equals("")) {
                decreaseSubCount(comment.getParentId());
            }
            communityRepository.deleteById(id);
        }
    }


    /**
     * 댓글 삭제 요청시
     * 부모 댓글 subCount 감소 (대댓글일때)
     */
    public void decreaseSubCount(String parentId){
        Comment comment = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(parentId)), Comment.class);
        if(comment != null){
            comment.setSubCount(comment.getSubCount() - 1);
            mongoTemplate.save(comment,"comment");
        }
    }


}
