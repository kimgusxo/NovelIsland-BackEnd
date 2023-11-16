package com.example.novelisland.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ElasticSearchNovelRepositoryTest {

    private final ElasticSearchNovelRepository elasticSearchNovelRepository;

    @Autowired
    public ElasticSearchNovelRepositoryTest(ElasticSearchNovelRepository elasticSearchNovelRepository) {
        this.elasticSearchNovelRepository = elasticSearchNovelRepository;
    }

}
