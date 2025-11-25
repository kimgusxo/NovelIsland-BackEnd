package com.example.novelisland.controller;

import com.example.novelisland.format.Message;
import com.example.novelisland.service.ElasticSearchNovelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/elastics")
public class ElasticSearchNovelController {

    private final ElasticSearchNovelService elasticSearchNovelService;

    @Autowired
    public ElasticSearchNovelController(ElasticSearchNovelService elasticSearchNovelService) {
        this.elasticSearchNovelService = elasticSearchNovelService;
    }

    @GetMapping
    public ResponseEntity<Message> getNovelsByNovelExplanation(@RequestParam("novelExplanation") String novelExplanation) {
        log.info("getNovelByNovelExplanation: {}", novelExplanation);
        return new ResponseEntity<>(Message.of("소설 검색 성공", HttpStatus.OK.value(),
                elasticSearchNovelService.getElasticNovelsByNovelExplanation(novelExplanation)), HttpStatus.OK);
    }

    @PostMapping("/all")
    public String insertAll() {
        elasticSearchNovelService.insertAllNovels();
        return "데이터 인덱싱 완료";
    }
}
