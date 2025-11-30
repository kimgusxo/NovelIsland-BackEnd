package com.example.novelisland.controller;

import com.example.novelisland.format.Message;
import com.example.novelisland.service.AuthorService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    @ApiOperation(value = "정렬된 작가 데이터 받아오기", notes = "작가를 이름으로 정렬하여 페이징 조회한다.")
    public ResponseEntity<Message<?>> getSortingAuthor(@RequestParam("page") Integer page,
                                                       @RequestParam("size") Integer size) {
        log.info("[GET /authors] 정렬된 작가 목록 조회 요청 - page={}, size={}", page, size);

        return ResponseEntity.ok(
                Message.success(
                        "정렬된 작가 가져오기 완료",
                        authorService.getSortingAuthor(page, size)
                )
        );
    }

    @GetMapping("/{authorId}")
    @ApiOperation(value = "아이디로 작가 검색", notes = "작가 아이디로 작가를 검색한다.")
    public ResponseEntity<Message<?>> getAuthorByAuthorId(@PathVariable Long authorId) {
        log.info("[GET /authors/{}] 작가 단건 조회 요청", authorId);

        return ResponseEntity.ok(
                Message.success(
                        "작가 검색 성공",
                        authorService.getAuthorByAuthorId(authorId)
                )
        );
    }

    @GetMapping("/authorName")
    @ApiOperation(value = "이름으로 작가 검색", notes = "작가 이름으로 작가를 검색한다.")
    public ResponseEntity<Message<?>> getAuthorByAuthorName(@RequestParam("authorName") String authorName,
                                                            @RequestParam("page") Integer page,
                                                            @RequestParam("size") Integer size) {
        log.info("[GET /authors/authorName] 작가 이름 검색 요청 - authorName={}, page={}, size={}",
                authorName, page, size);

        return ResponseEntity.ok(
                Message.success(
                        "작가 검색 성공",
                        authorService.getAuthorByAuthorName(authorName, page, size)
                )
        );
    }
}
