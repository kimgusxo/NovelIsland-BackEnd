package com.example.novelisland.controller;

import com.example.novelisland.format.Message;
import com.example.novelisland.service.TagService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/get/sorting")
    @ApiOperation(value = "정렬된 태그 리스트 검색", notes = "정렬된 태그 리스트를 검색한다.")
    public ResponseEntity<Message> getSortingTags() {
        log.info("getSortingTags");
        return new ResponseEntity<>(Message.of("소설 리스트 검색 성공", HttpStatus.OK.value(), tagService.getSortingTags()), HttpStatus.OK);
    }

    @GetMapping("/find/tagId")
    @ApiOperation(value = "태그 아이디로 태그 검색", notes = "태그 아이디로 해당 태그를 검색한다.")
    public ResponseEntity<Message> getTagByTagId(@RequestParam("tagId") Long tagId) {
        log.info("getTagByTagId: {}", tagId);
        return new ResponseEntity<>(Message.of("태그 검색 성공", HttpStatus.OK.value(),
                tagService.getTagByTagId(tagId)), HttpStatus.OK);
    }

    @GetMapping("/find/tagId/novels")
    @ApiOperation(value = "태그 아이디로 소설검색", notes = "해당 태그의 소설 리스트를 검색한다.")
    public ResponseEntity<Message> getNovelsByTagId(@RequestParam("tagId") Long tagId,
                                                  @RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size) {
        log.info("getNovelsTagId: {}", tagId);
        return new ResponseEntity<>(Message.of("소설 리스트 검색 성공", HttpStatus.OK.value(),
               tagService.getNovelsByTagId(tagId, page, size)), HttpStatus.OK);
    }

    @GetMapping("/find/tagClassification/novels")
    @ApiOperation(value = "태그 이름으로 소설검색", notes = "해당 태그의 소설 리스트를 검색한다.")
    public ResponseEntity<Message> getNovelsByTagClassification(@RequestParam("tagClassification") String tagClassification,
                                                                @RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        log.info("getNovelsByTagClassification: {}", tagClassification);
        return new ResponseEntity<>(Message.of("소설 리스트 검색 성공", HttpStatus.OK.value(),
                tagService.getNovelsByTagClassification(tagClassification, page, size)), HttpStatus.OK);
    }
}
