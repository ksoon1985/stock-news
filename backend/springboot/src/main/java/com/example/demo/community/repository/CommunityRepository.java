package com.example.demo.community.repository;

import com.example.demo.community.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommunityRepository extends MongoRepository<Comment,String> {
    public List<Comment> findByCodeOrderByRegDateAsc(String code);
    public List<Comment> findByCodeAndParentIdOrderByRegDateAsc(String code,String parentId);
    public List<Comment> findByParentIdOrderByRegDateAsc(String parentId);
}
