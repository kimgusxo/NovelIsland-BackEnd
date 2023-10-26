package com.example.novelisland.controller;

import com.example.novelisland.format.Message;
import com.example.novelisland.service.BookMarkService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bookmark")
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @Autowired
    public BookMarkController(BookMarkService bookMarkService) {
        this.bookMarkService = bookMarkService;
    }

    // 여기는 고민해봐야 할듯??
    @GetMapping("/find/userIndex")
    @ApiOperation(value = "유저 인덱스로 북마크 리스트 검색", notes = "유저 인덱스로 해당 유저의 북마크된 소설리스트를 가져온다.")
    public ResponseEntity<Message> getBookMarkListByUserIndex(@RequestParam("userIndex") Long userIndex,
                                                              @RequestParam("page") Integer page,
                                                              @RequestParam("size") Integer size) {
        log.info("getBookMarkListByUserIndex: {}", userIndex);
        return new ResponseEntity<>(Message.of("북마크 리스트 검색 성공", HttpStatus.OK.value(),
                bookMarkService.getBookMarkListByUserIndex(userIndex, page, size)), HttpStatus.OK);
    }

    @PostMapping("/create/userIndex/and/novelId")
    @ApiOperation(value = "유저의 북마크 등록", notes = "유저 인덱스로 해당 유저의 북마크를 생성한다.")
    public ResponseEntity<Message> createBookMarkByUserIndex(@RequestParam("userIndex") Long userIndex,
                                                             @RequestParam("novelId") Long novelId) {
        log.info("createBookMarkByUserIndex: {}, {}", userIndex, novelId);
        return new ResponseEntity<>(Message.of("북마크 등록 성공", HttpStatus.OK.value(),
                bookMarkService.createBookMarkByUserIndex(userIndex, novelId)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/bookMarkId")
    @ApiOperation(value = "유저의 북마크 삭제", notes = "유저 인덱스로 해당 유저의 북마크를 해제한다")
    public ResponseEntity<Message> deleteBookMarkByBookMarkId(@RequestParam("bookMarkId") Long bookMarkId) {
        log.info("deleteBookMarkByBookMarkId: {}", bookMarkId);
        bookMarkService.deleteBookMarkByBookMarkId(bookMarkId);
        return new ResponseEntity<>(Message.of("북마크 해제 성공", HttpStatus.OK.value()), HttpStatus.OK);
    }
}
