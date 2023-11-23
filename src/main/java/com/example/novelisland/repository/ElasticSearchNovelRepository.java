package com.example.novelisland.repository;

import com.example.novelisland.document.ElasticSearchNovel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElasticSearchNovelRepository extends ElasticsearchRepository<ElasticSearchNovel, Long> {
    @Query("{\"multi_match\": {\"query\": \"?0\", \"fields\": [\"novel_name\", \"novel_explanation\"]}}")
    List<ElasticSearchNovel> findElasticNovelsByNovelExplanation(@Param("novel_explanation") String novelExplanation, Pageable pageable);

}
