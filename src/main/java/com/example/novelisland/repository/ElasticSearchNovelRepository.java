package com.example.novelisland.repository;

import com.example.novelisland.document.ElasticSearchNovel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticSearchNovelRepository extends ElasticsearchRepository<ElasticSearchNovel, Long> {

    List<ElasticSearchNovel> findAll();

}
