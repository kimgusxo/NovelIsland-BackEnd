package com.example.novelisland.controller;

import com.example.novelisland.format.Message;
import com.example.novelisland.service.BookMarkService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmarks")
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @GetMapping("/{userIndex}")
    @ApiOperation(value = "유저 인덱스로 북마크 리스트 검색", notes = "유저 인덱스로 해당 유저의 북마크된 소설리스트를 가져온다.")
    public ResponseEntity<Message<?>> getBookMarkListByUserIndex(@PathVariable("userIndex") Long userIndex,
                                                              @RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size) {
        log.info("[GET /bookmarks/{}] 북마크 리스트 조회 요청 - userIndex={}, page={}, size={}",
                userIndex, userIndex, page, size);

        return ResponseEntity.ok(
                Message.success(
                        "북마크 리스트 검색 성공",
                        bookMarkService.getBookMarkListByUserIndex(userIndex, page, size)
                )
        );
    }

    @PostMapping
    @ApiOperation(value = "유저의 북마크 등록", notes = "유저 인덱스로 해당 유저의 북마크를 생성한다.")
    public ResponseEntity<Message<?>> createBookMarkByUserIndex(@RequestParam("userIndex") Long userIndex,
                                                             @RequestParam("novelId") Long novelId) {
        log.info("[POST /bookmarks] 북마크 생성 요청 - userIndex={}, novelId={}", userIndex, novelId);

        return ResponseEntity.ok(
                Message.success(
                        "북마크 등록 성공",
                        bookMarkService.createBookMarkByUserIndex(userIndex, novelId)
                )
        );
    }

    @DeleteMapping("/{bookMarkId}")
    @ApiOperation(value = "유저의 북마크 삭제", notes = "유저 인덱스로 해당 유저의 북마크를 해제한다")
    public ResponseEntity<Message<?>> deleteBookMarkByBookMarkId(@PathVariable("bookMarkId") Long bookMarkId) {
        log.info("[DELETE /bookmarks/{}] 북마크 삭제 요청 - bookMarkId={}", bookMarkId, bookMarkId);

        bookMarkService.deleteBookMarkByBookMarkId(bookMarkId);

        return ResponseEntity.ok(
                Message.success("북마크 해제 성공")
        );
    }
}
