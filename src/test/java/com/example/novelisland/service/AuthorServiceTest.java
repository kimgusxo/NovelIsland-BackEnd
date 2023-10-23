package com.example.novelisland.service;

import com.example.novelisland.domain.Author;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.dto.AuthorDTO;
import com.example.novelisland.exception.author.NotExistAuthorException;
import com.example.novelisland.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        log.info("테스트 DTO 생성");

        Long authorId = 1L;
        String authorName = "test";
        List<Novel> novelList = new ArrayList<>();

        author = Author.builder()
                .authorId(authorId)
                .authorName(authorName)
                .novelList(novelList)
                .build();

        log.info("테스트 DTO 생성완료");
    }

    @Test
    @DisplayName("작가 아이디로 작가 검색 테스트 성공")
    void testGetAuthorByAuthorId_성공() {
        log.info("작가 아이디로 작가 검색 테스트 시작");

        // given
        when(authorRepository.existsByAuthorId(author.getAuthorId())).thenReturn(true);
        when(authorRepository.findByAuthorId(author.getAuthorId())).thenReturn(author);

        // when
        AuthorDTO authorDTO = authorService.getAuthorByAuthorId(author.getAuthorId());

        // then
        assertThat(authorDTO).isNotNull();

        log.info("작가 아이디로 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 아이디로 작가 검색 테스트 실패")
    void testGetAuthorByAuthorId_실패() {
        log.info("작가 아이디로 작가 검색 테스트 시작");

        // given
        when(authorRepository.existsByAuthorId(author.getAuthorId())).thenReturn(false);

        // when

        // then
        assertThatThrownBy(() -> authorService.getAuthorByAuthorId(author.getAuthorId()))
                .isInstanceOf(NotExistAuthorException.class);

        log.info("작가 아이디로 작가 검색 테스트 종료");
    }


    @Test
    @DisplayName("작가 이름으로 작가 검색 테스트 성공")
    void testGetAuthorByAuthorName_성공() {
        log.info("작가 이름으로 작가 검색 테스트 시작");

        // given
        when(authorRepository.existsByAuthorName(author.getAuthorName())).thenReturn(true);
        when(authorRepository.findByAuthorName(author.getAuthorName())).thenReturn(author);

        // when
        AuthorDTO authorDTO = authorService.getAuthorByAuthorName(author.getAuthorName());

        // then
        assertThat(authorDTO).isNotNull();

        log.info("작가 이름으로 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 이름으로 작가 검색 테스트 실패")
    void testGetAuthorByAuthorName_실패() {
        log.info("작가 이름으로 작가 검색 테스트 시작");

        // given
        when(authorRepository.existsByAuthorName(author.getAuthorName())).thenReturn(false);

        // when

        // then
        assertThatThrownBy(() -> authorService.getAuthorByAuthorName(author.getAuthorName()))
                .isInstanceOf(NotExistAuthorException.class);

        log.info("작가 이름으로 작가 검색 테스트 종료");
    }
}