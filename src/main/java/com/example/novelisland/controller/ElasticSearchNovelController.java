//package com.example.novelisland.controller;
//
//import com.example.novelisland.document.ElasticSearchNovel;
//import com.example.novelisland.service.ElasticSearchNovelService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.query.Param;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/elastic")
//public class ElasticSearchNovelController {
//
//    private final ElasticSearchNovelService elasticSearchNovelService;
//
//    @Autowired
//    public ElasticSearchNovelController(ElasticSearchNovelService elasticSearchNovelService) {
//        this.elasticSearchNovelService = elasticSearchNovelService;
//    }
//
//    @PostMapping("/insert")
//    public ElasticSearchNovel insertNovel(@RequestBody ElasticSearchNovel elasticSearchNovel) {
//       return elasticSearchNovelService.insertNovel(elasticSearchNovel);
//    }
//}
