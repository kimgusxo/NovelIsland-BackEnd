package com.example.novelisland.controller;

import com.example.novelisland.format.Message;
import com.example.novelisland.service.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @GetMapping("/sorted")
    @ApiOperation(value = "정렬된 태그 리스트 검색", notes = "정렬된 태그 리스트를 검색한다.")
    public ResponseEntity<Message<?>> getSortingTags() {
        log.info("[GET /tags/sorted] 정렬된 태그 리스트 조회 요청");

        return ResponseEntity.ok(
                Message.success(
                        "태크 검색 성공",
                        tagService.getSortingTags()
                )
        );
    }

    @GetMapping("/{tagId}")
    @ApiOperation(value = "태그 아이디로 태그 검색", notes = "태그 아이디로 해당 태그를 검색한다.")
    public ResponseEntity<Message<?>> getTagByTagId(@PathVariable("tagId") Long tagId) {
        log.info("[GET /tags/{}] 태그 단건 조회 요청 - tagId={}", tagId, tagId);

        return ResponseEntity.ok(
                Message.success(
                        "태그 검색 성공",
                        tagService.getTagByTagId(tagId)
                )
        );
    }

    @GetMapping("/{tagId}/novels")
    @ApiOperation(value = "태그 아이디로 소설검색", notes = "해당 태그의 소설 리스트를 검색한다.")
    public ResponseEntity<Message<?>> getNovelsByTagId(@PathVariable("tagId") Long tagId,
                                                       @RequestParam("page") Integer page,
                                                       @RequestParam("size") Integer size) {
        log.info("[GET /tags/{}/novels] 태그 아이디로 소설 리스트 조회 요청 - tagId={}, page={}, size={}",
                tagId, tagId, page, size);

        return ResponseEntity.ok(
                Message.success(
                        "소설 리스트 검색 성공",
                        tagService.getNovelsByTagId(tagId, page, size)
                )
        );
    }

    @GetMapping("/searched/novels")
    @ApiOperation(value = "태그 이름으로 소설검색", notes = "해당 태그의 소설 리스트를 검색한다.")
    public ResponseEntity<Message<?>> getNovelsByTagClassification(
            @RequestParam("tagClassification") String tagClassification,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {

        log.info("[GET /tags/searched/novels] 태그 분류로 소설 리스트 조회 요청 - tagClassification={}, page={}, size={}",
                tagClassification, page, size);

        return ResponseEntity.ok(
                Message.success(
                        "소설 리스트 검색 성공",
                        tagService.getNovelsByTagClassification(tagClassification, page, size)
                )
        );
    }
}
