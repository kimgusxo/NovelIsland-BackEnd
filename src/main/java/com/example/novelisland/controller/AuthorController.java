package com.example.novelisland.controller;

import com.example.novelisland.format.Message;
import com.example.novelisland.service.AuthorService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/find/authorId")
    @ApiOperation(value = "아이디로 작가 검색", notes = "작가 아이디로 작가를 검색한다.")
    public ResponseEntity<Message> getAuthorByAuthorId(@RequestParam("authorId") Long authorId) {
        log.info("getAuthorByAuthorId: {}", authorId);
        return new ResponseEntity<>(Message.of("작가 검색 성공", HttpStatus.OK.value(), authorService.getAuthorByAuthorId(authorId)), HttpStatus.OK);
    }

    @GetMapping("/find/authorName")
    @ApiOperation(value = "이름으로 작가 검색", notes = "작가 이름으로 작가를 검색한다.")
    public ResponseEntity<Message> getAuthorByAuthorName(@RequestParam("authorName") String authorName) {
        log.info("getAuthorByAuthorName: {}", authorName);
        return new ResponseEntity<>(Message.of("작가 검색 성공", HttpStatus.OK.value(), authorService.getAuthorByAuthorName(authorName)), HttpStatus.OK);
    }
}
