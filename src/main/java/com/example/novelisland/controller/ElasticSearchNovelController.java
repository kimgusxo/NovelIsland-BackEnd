package com.example.novelisland.controller;

import com.example.novelisland.format.Message;
import com.example.novelisland.service.ElasticSearchNovelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/elastics")
public class ElasticSearchNovelController {

    private final ElasticSearchNovelService elasticSearchNovelService;

    @GetMapping
    public ResponseEntity<Message<?>> getNovelsByNovelExplanation(@RequestParam("novelExplanation") String novelExplanation) {
        log.info("[GET /elastics] 소설 설명 기반 검색 요청 - novelExplanation={}", novelExplanation);

        return ResponseEntity.ok(
                Message.success(
                        "소설 검색 성공",
                        elasticSearchNovelService.getElasticNovelsByNovelExplanation(novelExplanation)
                )
        );
    }

    @PostMapping("/all")
    public ResponseEntity<Message<?>> insertAll() {
        log.info("[POST /elastics/all] 전체 소설 인덱싱 요청");

        elasticSearchNovelService.insertAllNovels();

        return ResponseEntity.ok(
                Message.success("데이터 인덱싱 완료")
        );
    }
}
