package com.example.novelisland.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ElasticSearchNovelRepositoryTest {

    private final ElasticSearchNovelRepository elasticSearchNovelRepository;

    private int page;
    private int size;
    private Pageable pageable;

    @Autowired
    public ElasticSearchNovelRepositoryTest(ElasticSearchNovelRepository elasticSearchNovelRepository) {
        this.elasticSearchNovelRepository = elasticSearchNovelRepository;
    }
    
    @BeforeEach
    void setUp() {
        log.info("페이지네이션 설정");

        log.info("페이지네이션 설정 완료");
    }
    
    @Test
    @DisplayName("문장형 검색어로 검색어와 유사한 소설 검색 테스트 성공")
    void testFindElasticNovelsByNovelExplanation() {
        log.info("문장형 검색어로 검색어와 유사한 소설 검색 테스트 시작");
        
        // given
        
        // when
        
        // then

        log.info("문장형 검색어로 검색어와 유사한 소설 검색 테스트 종료");
    }

}
