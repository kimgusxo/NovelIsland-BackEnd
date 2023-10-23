package com.example.novelisland.integration;

import com.example.novelisland.format.ErrorMessage;
import com.example.novelisland.format.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("작가 아이디로 작가 검색 테스트")
    void testGetAuthorByAuthorId_성공() {
        log.info("작가 아이디로 작가 검색 테스트 시작");

        Long authorId = 1L;

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/author/find/authorId?authorId=" + authorId),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("작가 아이디로 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("잘못된 작가 아이디로 작가 검색 테스트")
    void testGetAuthorByAuthorId_실패() {
        log.info("잘못된 작가 아이디로 작가 검색 테스트 시작");

        Long authorId = 100000L;

        ResponseEntity<ErrorMessage> response = restTemplate.getForEntity(
                createURLWithPort("/author/find/authorId?authorId=" + authorId),
                ErrorMessage.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        ErrorMessage errorMessage = response.getBody();

        assert errorMessage != null;
        System.out.println(errorMessage.getMessage());

        log.info("잘못된 작가 아이디로 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 이름으로 작가 검색 테스트 테스트")
    void testGetAuthorByAuthorName_성공() {
        log.info("작가 이름으로 작가 검색 테스트 시작");

        String authorName = "윤이수";

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/author/find/authorName?authorName=" + authorName),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());


        log.info("작가 이름으로 작가 검색 테스트 종료");
    }

    @Test
    @DisplayName("잘못된 작가 이름으로 작가 검색 테스트")
    void testGetAuthorByAuthorName_실패() {
        log.info("잘못된 작가 이름으로 작가 검색 테스트 시작");

        String authorName = "이치영";

        ResponseEntity<ErrorMessage> response = restTemplate.getForEntity(
                createURLWithPort("/author/find/authorName?authorName=" + authorName),
                ErrorMessage.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        ErrorMessage errorMessage = response.getBody();

        assert errorMessage != null;
        System.out.println(errorMessage.getMessage());

        log.info("잘못된 작가 이름으로 작가 검색 테스트 종료");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
