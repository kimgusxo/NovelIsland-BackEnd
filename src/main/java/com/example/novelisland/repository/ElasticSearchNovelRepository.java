package com.example.novelisland.repository;

import com.example.novelisland.document.ElasticSearchNovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ElasticSearchNovelRepository extends ElasticsearchRepository<ElasticSearchNovel, Long> {

    List<ElasticSearchNovel> findAll();
    Page<ElasticSearchNovel> findAll(Pageable pageable);
    List<ElasticSearchNovel> findByNovelExplanationContaining(String novelExplanation);

}
