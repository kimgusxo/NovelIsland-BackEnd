package com.example.novelisland.controller;

import com.example.novelisland.dto.SearchDTO;
import com.example.novelisland.format.Message;
import com.example.novelisland.service.NovelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/novel")
public class NovelController {

    private final NovelService novelService;

    @Autowired
    public NovelController(NovelService novelService) {
        this.novelService = novelService;
    }

    @GetMapping("/find/novelId")
    @ApiOperation(value = "아이디로 소설 검색", notes = "소설 아이디로 소설 1개를 검색한다.")
    public ResponseEntity<Message> getNovelByNovelId(@RequestParam("novelId") Long novelId) {
        return new ResponseEntity<>(Message.of("소설 검색 완료", HttpStatus.OK.value(), novelService.getNovelByNovelId(novelId)), HttpStatus.OK);
    }

    @GetMapping("/find/novelName")
    @ApiOperation(value = "이름으로 소설 목록 검색", notes = "소설 이름으로 소설 리스트를 검색한다.")
    public ResponseEntity<Message> getNovelsByNovelName(@RequestParam("novelName") String novelName,
                                                        @RequestParam("page") Integer page,
                                                        @RequestParam("size") Integer size) {
        return new ResponseEntity<>(Message.of("소설 검색 완료", HttpStatus.OK.value(),
                novelService.getNovelsByNovelName(novelName, page, size)), HttpStatus.OK);
    }

    @GetMapping("/find/novelName/and/tagId")
    @ApiOperation(value = "이름과 장르들로 소설 목록 검색", notes = "소설 이름과 태그 리스트로 소설 리스트를 검색한다.")
    public ResponseEntity<Message> getNovelsByNovelNameContainingAndTagIdList(@ModelAttribute SearchDTO searchDTO,
                                                                              @RequestParam("page") Integer page,
                                                                              @RequestParam("size") Integer size) {
        return new ResponseEntity<>(Message.of("소설 검색 완료", HttpStatus.OK.value(),
                novelService.getNovelsByNovelNameContainingAndTagIdList(searchDTO, page, size)), HttpStatus.OK);
    }
}
