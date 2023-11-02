package com.example.novelisland.controller;

import com.example.novelisland.document.ElasticSearchNovel;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.repository.ElasticSearchNovelRepository;
import com.example.novelisland.repository.NovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/elastic")
public class ElasticSearchNovelController {

    private final ElasticSearchNovelRepository elasticSearchNovelRepository;
    private final NovelRepository novelRepository;

    @Autowired
    public ElasticSearchNovelController(ElasticSearchNovelRepository elasticSearchNovelRepository,
                                        NovelRepository novelRepository) {
        this.elasticSearchNovelRepository = elasticSearchNovelRepository;
        this.novelRepository = novelRepository;
    }

    @GetMapping("/move")
    public void move() {
        List<Novel> novelList = novelRepository.findAll();

        for(Novel novel : novelList) {
            ElasticSearchNovel elasticSearchNovel = ElasticSearchNovel.builder()
                                .novelId(novel.getNovelId())
                                .novelExplanation(novel.getNovelExplanation())
                                .build();
            elasticSearchNovelRepository.save(elasticSearchNovel);
        }
    }

    @PostMapping("/insert")
    public ElasticSearchNovel insertNovel() {
        ElasticSearchNovel novel = ElasticSearchNovel.builder()
                .novelId(2L)
                .novelExplanation("안녕하세요.")
                .build();
        novel = elasticSearchNovelRepository.save(novel);
        return novel;
    }

    @GetMapping("/get")
    public List<ElasticSearchNovel> getNovels() {
        Pageable pageable = PageRequest.of(0, 1000);

        List<ElasticSearchNovel> elasticSearchNovels = elasticSearchNovelRepository.findAll(pageable).getContent();

        for(ElasticSearchNovel elasticSearchNovel : elasticSearchNovels) {
            System.out.println(elasticSearchNovel);
        }

        return elasticSearchNovels;
    }

    @GetMapping("/get/novelExplanation")
    public List<ElasticSearchNovel> getNovelByNovelExplanation(@RequestParam("novelExplanation") String novelExplanation) {
        return elasticSearchNovelRepository.findByNovelExplanationContaining(novelExplanation);
    }
}
