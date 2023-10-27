package com.example.novelisland.controller;

import com.example.novelisland.format.Message;
import com.example.novelisland.service.NovelService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/novel")
public class NovelController {

    private final NovelService novelService;

    @Autowired
    public NovelController(NovelService novelService) {
        this.novelService = novelService;
    }

    @GetMapping("/get/random")
    @ApiOperation(value = "랜덤 소설 데이터 받아오기", notes = "랜덤 소설을 가져온다.")
    public ResponseEntity<Message> getRandomNovels() {
        log.info("getRandomNovels");
        return new ResponseEntity<>(Message.of("랜덤 소설 가져오기 완료", HttpStatus.OK.value(), novelService.getRandomNovels()), HttpStatus.OK);
    }

    @GetMapping("/get/ranking")
    @ApiOperation(value = "인기순 소설 데이터 받아오기", notes = "소설을 인기순으로 가져온다.")
    public ResponseEntity<Message> getRankingNovels() {
        log.info("getRankingNovels");
        return new ResponseEntity<>(Message.of("인기순 소설 가져오기 완료", HttpStatus.OK.value(), novelService.getRankingNovels()), HttpStatus.OK);
    }

    @GetMapping("/get/sorting")
    @ApiOperation(value = "정렬된 소설 데이터 받아오기", notes = "소설을 이름으로 정렬하여 가져온다.")
    public ResponseEntity<Message> getSortingNovels() {
        log.info("getSortingNovels");
        return new ResponseEntity<>(Message.of("정렬된 소설 가져오기 완료", HttpStatus.OK.value(), novelService.getSortingNovels()), HttpStatus.OK);
    }

    @GetMapping("/find/novelId")
    @ApiOperation(value = "아이디로 소설 검색", notes = "소설 아이디로 소설 1개를 검색한다.")
    public ResponseEntity<Message> getNovelByNovelId(@RequestParam("novelId") Long novelId) {
        log.info("getNovelByNovelId: {}", novelId);
        return new ResponseEntity<>(Message.of("소설 검색 완료", HttpStatus.OK.value(), novelService.getNovelByNovelId(novelId)), HttpStatus.OK);
    }

    @GetMapping("/find/novelName")
    @ApiOperation(value = "이름으로 소설 목록 검색", notes = "소설 이름으로 소설 리스트를 검색한다.")
    public ResponseEntity<Message> getNovelsByNovelName(@RequestParam("novelName") String novelName,
                                                        @RequestParam("page") Integer page,
                                                        @RequestParam("size") Integer size) {
        log.info("getNovelsByNovelName: {}", novelName);
        return new ResponseEntity<>(Message.of("소설 검색 완료", HttpStatus.OK.value(),
                novelService.getNovelsByNovelName(novelName, page, size)), HttpStatus.OK);
    }

    @GetMapping("/find/authorId")
    @ApiOperation(value = "이름으로 소설 목록 검색", notes = "소설 이름으로 소설 리스트를 검색한다.")
    public ResponseEntity<Message> getNovelsByAuthorId(@RequestParam("authorId") Long authorId,
                                                        @RequestParam("page") Integer page,
                                                        @RequestParam("size") Integer size) {
        log.info("getNovelsByAuthorId: {}", authorId);
        return new ResponseEntity<>(Message.of("소설 검색 완료", HttpStatus.OK.value(),
                novelService.getNovelsByAuthorId(authorId, page, size)), HttpStatus.OK);
    }

    @GetMapping("/find/novelName/and/tagId")
    @ApiOperation(value = "이름과 장르들로 소설 목록 검색", notes = "소설 이름과 태그 리스트로 소설 리스트를 검색한다.")
    public ResponseEntity<Message> getNovelsByNovelNameContainingAndTagIdList(@RequestParam("novelName") String novelName,
                                                                              @RequestParam("tagIdList") List<Long> tagIdList,
                                                                              @RequestParam("page") Integer page,
                                                                              @RequestParam("size") Integer size) {
        log.info("getNovelsByNovelNameContainingAndTagIdList: {}, {}", novelName, tagIdList);
        return new ResponseEntity<>(Message.of("소설 검색 완료", HttpStatus.OK.value(),
                novelService.getNovelsByNovelNameContainingAndTagIdList(novelName, tagIdList, page, size)), HttpStatus.OK);
    }
}
