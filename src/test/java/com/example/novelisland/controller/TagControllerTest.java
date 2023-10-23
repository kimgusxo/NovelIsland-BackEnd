package com.example.novelisland.controller;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.exception.novel.NotExistNovelException;
import com.example.novelisland.service.BookMarkService;
import com.example.novelisland.service.TagService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(TagController.class)
@AutoConfigureMockMvc(addFilters = false)
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

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
    @DisplayName("태그 아이디로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByTagId_성공() throws Exception {
        log.info("태그 아이디로 소설 리스트 검색 테스트 시작");

        // given
        Long tagId = 1L;
        given(tagService.getNovelsByTagId(tagId, page, size)).willReturn(new ArrayList<>());

        // when & then
        mockMvc.perform(get("/tag/find/tagId")
                .param("tagId", "1")
                        .param("page", "0")
                        .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());

        log.info("태그 아이디로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("태그 아이디로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByTagId() throws Exception {
        log.info("태그 아이디로 소설 리스트 검색 테스트 시작");

        // given
        Long tagId = 1L;
        given(tagService.getNovelsByTagId(tagId, page, size)).willThrow(new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN));

        // when & then
        mockMvc.perform(get("/tag/find/tagId")
                        .param("tagId", "1")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("태그 아이디로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("태그 이름으로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByTagName_성공() throws Exception {
        log.info("태그 이름으로 소설 리스트 검색 테스트 시작");

        // given
        String tagClassification = "test";
        given(tagService.getNovelsByTagClassification(tagClassification, page, size)).willReturn(new ArrayList<>());

        // when & then
        mockMvc.perform(get("/tag/find/tagClassification")
                        .param("tagClassification", "test")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        log.info("태그 이름으로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("태그 이름으로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByTagName_실패() throws Exception {
        log.info("태그 이름으로 소설 리스트 검색 테스트 시작");

        // given
        String tagClassification = "test";
        given(tagService.getNovelsByTagClassification(tagClassification, page, size)).willThrow(new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN));

        // when & then
        mockMvc.perform(get("/tag/find/tagClassification")
                        .param("tagClassification", "test")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("태그 이름으로 소설 리스트 검색 테스트 종료");
    }
}