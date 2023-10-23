package com.example.novelisland.controller;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.dto.BookMarkDTO;
import com.example.novelisland.exception.bookmark.AlreadyExistBookMarkException;
import com.example.novelisland.exception.bookmark.NotExistBookMarkException;
import com.example.novelisland.service.BookMarkService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(BookMarkController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookMarkControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookMarkService bookMarkService;

    private Integer page;
    private Integer size;

    @BeforeEach
    void setUp() {
        log.info("페이지 변수 설정");
        
        page = 0;
        size = 10;

        log.info("페이지 변수 설정완료");
    }

    @Test
    @DisplayName("유저의 북마크 검색 테스트 성공")
    void testGetBookMarkListByUserIndex_성공() throws Exception {
        log.info("유저의 북마크 검색 테스트 시작");

        // given
        Long userIndex = 1L;
        given(bookMarkService.getBookMarkListByUserIndex(userIndex, page, size)).willReturn(new ArrayList<>());

        // when & then
        mockMvc.perform(get("/bookmark/find/userIndex")
                .param("userIndex", "1")
                        .param("page", "0")
                        .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        log.info("유저의 북마크 검색 테스트 종료");
    }

    @Test
    @DisplayName("유저의 북마크 검색 테스트 실패")
    void testGetBookMarkListByUserIndex_실패() throws Exception {
        log.info("유저의 북마크 검색 테스트 시작");

        // given
        Long userIndex = 1L;
        given(bookMarkService.getBookMarkListByUserIndex(userIndex, page, size)).willThrow(new NotExistBookMarkException(ErrorCode.NOT_EXIST_BOOKMARK_TOKEN));

        // when & then
        mockMvc.perform(get("/bookmark/find/userIndex")
                        .param("userIndex", "1")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("유저의 북마크 검색 테스트 종료");
    }

    @Test
    @DisplayName("유저의 북마크 등록 테스트 성공")
    void testCreateBookMarkByBookMarkId_성공() throws Exception {
        log.info("유저의 북마크 등록 테스트 시작");

        // given
        Long userIndex = 1L;
        Long novelId = 1L;

        given(bookMarkService.createBookMarkByUserIndex(userIndex, novelId)).willReturn(new BookMarkDTO());

        // when & then
        mockMvc.perform(post("/bookmark/create/userIndex/and/novelId")
                        .param("userIndex", "1")
                        .param("novelId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userindex\":\"1\",\"novelid\":\"1\"}"))
                .andExpect(status().isOk());

        log.info("유저의 북마크 등록 테스트 종료");
    }

    @Test
    @DisplayName("유저의 북마크 등록 테스트 실패")
    void testCreateBookMarkByBookMarkId_실패() throws Exception {
        log.info("유저의 북마크 등록 테스트 시작");

        // given
        Long userIndex = 1L;
        Long novelId = 1L;

        given(bookMarkService.createBookMarkByUserIndex(userIndex, novelId)).willThrow(new AlreadyExistBookMarkException(ErrorCode.AlREADY_EXIST_BOOKMARK_TOKEN));

        // when & then
        mockMvc.perform(post("/bookmark/create/userIndex/and/novelId")
                        .param("userIndex", "1")
                        .param("novelId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userindex\":\"1\",\"novelid\":\"1\"}"))
                .andExpect(status().isBadRequest());

        log.info("유저의 북마크 등록 테스트 종료");
    }

    @Test
    @DisplayName("유저의 북마크 해제 테스트 성공")
    void testDeleteBookMarkByBookMarkId_성공() throws Exception {
        log.info("유저의 북마크 해제 테스트 시작");

        // given
        Long bookMarkId = 1L;

        willDoNothing().given(bookMarkService).deleteBookMarkByBookMarkId(bookMarkId);

        // when & then
        mockMvc.perform(delete("/bookmark/delete/bookMarkId")
                .param("bookMarkId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        log.info("유저의 북마크 해제 테스트 종료");
    }

    @Test
    @DisplayName("유저의 북마크 해제 테스트_실패")
    void testDeleteBookMarkByBookMarkId_실패() throws Exception {
        log.info("유저의 북마크 해제 테스트 시작");

        // given
        Long bookMarkId = 1L;

        willThrow(new NotExistBookMarkException(ErrorCode.NOT_EXIST_BOOKMARK_TOKEN)).given(bookMarkService).deleteBookMarkByBookMarkId(bookMarkId);

        // when & then
        mockMvc.perform(delete("/bookmark/delete/bookMarkId")
                        .param("bookMarkId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("유저의 북마크 해제 테스트 종료");
    }
}