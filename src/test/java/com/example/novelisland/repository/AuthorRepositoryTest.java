package com.example.novelisland.repository;

import com.example.novelisland.domain.Author;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorRepositoryTest {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRepositoryTest(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    @DisplayName("작가 존재여부 찾기 테스트")
    void testExistsAuthorById() {
        log.info("작가 존재여부 찾기 테스트 시작");

        // given
        Long authorId = 1L;

        // when
        Boolean token = authorRepository.existsByAuthorId(authorId);

        // then
        assertThat(token)
                .as("작가가 존재하지 않습니다.")
                .isTrue();

        log.info("작가 존재여부 찾기 테스트 종료");
    }

    @Test
    @DisplayName("작가 이름 존재여부 찾기 테스트")
    void testExistsAuthorByName() {
        log.info("작가 이름 존재여부 찾기 테스트 시작");
        
        // given
        String authorName = "윤이수";

        // when
        Boolean token = authorRepository.existsByAuthorName(authorName);

        // then
        assertThat(token)
                .as("작가가 존재하지 않습니다.")
                .isTrue();

        log.info("작가 이름 존재여부 찾기 테스트 종료");
    }

    @Test
    @DisplayName("작가 아이디로 작가 검색 테스트")
    void testFindAuthorById() {
        log.info("작가 아이디로 작가 검색 테스트 시작");

        // given
        Long authorId = 1L;

        // when
        Author author = authorRepository.findByAuthorId(authorId);

        // then
        assertThat(author)
                .as("작가가 존재하지 않습니다.")
                .isNotNull();

        System.out.println("작가 인덱스: " + author.getAuthorId());
        System.out.println("작가 이름: " + author.getAuthorName());

        author.getNovelList().forEach(n ->
                System.out.println("소설 이름: " + n.getNovelName()));

        log.info("작가 아이디로 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 이름으로 작가 검색 테스트")
    void testFindAuthorByName() {
        log.info("작가 이름으로 작가 검색 테스트 시작");
        
        // given
        String authorName = "윤이수";

        // when
        Author author = authorRepository.findByAuthorName(authorName);

        // then
        assertThat(author)
                .as("작가가 존재하지 않습니다.")
                .isNotNull();

        System.out.println("작가 인덱스: " + author.getAuthorId());
        System.out.println("작가 이름: " + author.getAuthorName());

        author.getNovelList().forEach(n ->
                System.out.println(
                        "소설 이름: " + n.getNovelName()));
        log.info("작가 이름으로 작가 검색 테스트 종료");
    }
}
