package com.example.novelisland.service;

import com.example.novelisland.repository.ElasticSearchNovelRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ElasticSearchNovelServiceTest {

    @Mock
    private ElasticSearchNovelRepository elasticSearchNovelRepository;

    @InjectMocks
    private ElasticSearchNovelService elasticSearchNovelService;

}
