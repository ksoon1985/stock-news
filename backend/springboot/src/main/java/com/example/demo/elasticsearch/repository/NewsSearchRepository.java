package com.example.demo.elasticsearch.repository;

import com.example.demo.elasticsearch.model.News;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NewsSearchRepository extends ElasticsearchRepository<News,String> {
}
