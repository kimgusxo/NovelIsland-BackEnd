package com.example.novelisland.controller;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.dto.AuthorDTO;
import com.example.novelisland.exception.author.NotExistAuthorException;
import com.example.novelisland.service.AuthorService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("작가 아이디로 작가 검색 테스트 성공")
    void testGetAuthorByAuthorId_성공() throws Exception {
        log.info("작가 아이디로 작가 검색 테스트 시작");

        // given
        Long authorId = 1L;
        given(authorService.getAuthorByAuthorId(authorId)).willReturn(new AuthorDTO());

        // when & then
        mockMvc.perform(get("/author/find/authorId")
                        .param("authorId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        log.info("작가 아이디로 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 아이디로 작가 검색 테스트 실패")
    void testGetAuthorByAuthorId_실패() throws Exception {
        log.info("작가 아이디로 작가 검색 테스트 시작");

        // given
        Long authorId = 1L;
        given(authorService.getAuthorByAuthorId(authorId)).willThrow(new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN));

        // when & then
        mockMvc.perform(get("/author/find/authorId")
                        .param("authorId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("작가 아이디로 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 이름으로 작가 검색 테스트 성공")
    void testGetAuthorByAuthorName_성공() throws Exception {
        log.info("작가 이름으로 작가 검색 테스트 시작");
        
        // given
        String authorName = "test";
        given(authorService.getAuthorByAuthorName(authorName)).willReturn(new AuthorDTO());

        // when & then
        mockMvc.perform(get("/author/find/authorName")
                        .param("authorName", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        log.info("작가 이름으로 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 이름으로 작가 검색 테스트 실패")
    void testGetAuthorByAuthorName_실패() throws Exception {
        log.info("작가 이름으로 작가 검색 테스트 시작");

        // given
        String authorName = "test";
        given(authorService.getAuthorByAuthorName(authorName)).willThrow(new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN));

        // when & then
        mockMvc.perform(get("/author/find/authorName")
                        .param("authorName", "test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        log.info("작가 이름으로 작가 검색 테스트 종료");
    }

}