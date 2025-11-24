package com.example.novelisland.service;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.document.ElasticSearchNovel;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.dto.NovelDTO;
import com.example.novelisland.exception.novel.NotExistNovelException;
import com.example.novelisland.repository.ElasticSearchNovelRepository;
import com.example.novelisland.repository.NovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElasticSearchNovelService {

    private final ElasticSearchNovelRepository elasticSearchNovelRepository;
    private final NovelRepository novelRepository;

    @Autowired
    public ElasticSearchNovelService(ElasticSearchNovelRepository elasticSearchNovelRepository,
                                     NovelRepository novelRepository) {
        this.elasticSearchNovelRepository = elasticSearchNovelRepository;
        this.novelRepository = novelRepository;
    }

    @Transactional
    public List<NovelDTO> getElasticNovelsByNovelExplanation(String novelExplanation) {

        Pageable pageable = PageRequest.of(0, 9);

        List<ElasticSearchNovel> elasticSearchNovelList = elasticSearchNovelRepository.findElasticNovelsByNovelExplanation(novelExplanation, pageable);

        if(elasticSearchNovelList.isEmpty()) {
            throw new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN);
        } else {
            List<NovelDTO> novelDTOList = new ArrayList<>();

            for(ElasticSearchNovel elasticSearchNovel : elasticSearchNovelList) {
                NovelDTO novelDTO = novelRepository.findByNovelId(elasticSearchNovel.getNovelId()).toDTO();

                novelDTOList.add(novelDTO);
            }

            return novelDTOList;
        }
    }

    @Transactional
    public void insertAllNovels() {
        List<Novel> novels = novelRepository.findAll();

        int batchSize = 300;

        for (int i = 0; i < novels.size(); i += batchSize) {
            int end = Math.min(i + batchSize, novels.size());

            List<ElasticSearchNovel> chunk = novels.subList(i, end).stream()
                    .map(novel -> ElasticSearchNovel.builder()
                            .novelId(novel.getNovelId())
                            .novelName(novel.getNovelName())
                            .novelExplanation(novel.getNovelExplanation())
                            .build())
                    .collect(Collectors.toList());

            elasticSearchNovelRepository.saveAll(chunk);
        }
    }
}
