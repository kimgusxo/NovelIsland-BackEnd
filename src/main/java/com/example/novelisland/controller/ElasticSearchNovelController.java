//package com.example.novelisland.controller;
//
//import com.example.novelisland.document.ElasticSearchNovel;
//import com.example.novelisland.repository.ElasticSearchNovelRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/elastic")
//public class ElasticSearchNovelController {
//
//    private final ElasticSearchNovelRepository elasticSearchNovelRepository;
//
//    @Autowired
//    public ElasticSearchNovelController(ElasticSearchNovelRepository elasticSearchNovelRepository) {
//        this.elasticSearchNovelRepository = elasticSearchNovelRepository;
//    }
//
//    @PostMapping("/insert")
//    public ElasticSearchNovel insertNovel() {
//        ElasticSearchNovel novel = ElasticSearchNovel.builder()
//                .novelId(2L)
//                .novelExplanation("This is a test novel.")
//                .build();
//
//        novel = elasticSearchNovelRepository.save(novel);
//        return novel;
//    }
//
//    @PostMapping("/get")
//    public List<ElasticSearchNovel> getNovels() {
//        List<ElasticSearchNovel> elasticSearchNovels = elasticSearchNovelRepository.findAll();
//
//        for(ElasticSearchNovel elasticSearchNovel : elasticSearchNovels) {
//            System.out.println(elasticSearchNovel);
//        }
//
//        return elasticSearchNovels;
//    }
//}
