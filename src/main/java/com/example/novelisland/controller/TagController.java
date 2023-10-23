package com.example.novelisland.controller;

import com.example.novelisland.format.Message;
import com.example.novelisland.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/find/tagId")
    @ApiOperation(value = "태그 아이디로 소설검색", notes = "해당 태그의 소설 리스트를 검색한다.")
    public ResponseEntity<Message> getNovelsTagId(@RequestParam("tagId") Long tagId,
                                                  @RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size) {
       return new ResponseEntity<>(Message.of("소설 리스트 검색 성공", HttpStatus.OK.value(),
               tagService.getNovelsByTagId(tagId, page, size)), HttpStatus.OK);
    }

    @GetMapping("/find/tagClassification")
    @ApiOperation(value = "태그 이름으로 소설검색", notes = "해당 태그의 소설 리스트를 검색한다.")
    public ResponseEntity<Message> getNovelsByTagClassification(@RequestParam("tagClassification") String tagClassification,
                                                                @RequestParam("page") Integer page,
                                                                @RequestParam("size") Integer size) {
        return new ResponseEntity<>(Message.of("소설 리스트 검색 성공", HttpStatus.OK.value(),
                tagService.getNovelsByTagClassification(tagClassification, page, size)), HttpStatus.OK);
    }
}
