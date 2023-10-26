package com.example.novelisland.integration;

import com.example.novelisland.format.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NovelTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Integer page;
    private Integer size;

    @BeforeEach
    void setUp() {
        log.info("페이지 변수 설정");

        page = 0;
        size = 10;

        log.info("페이지 변수 설정");
    }

    @Test
    @DisplayName("소설 아이디로 소설 검색 테스트 성공")
    void testGetNovelByNovelId_성공() {
        log.info("소설 아이디로 소설 검색 테스트 시작");

        Long novelId = 4773L;

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/novelId?novelId=" + novelId),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("소설 아이디로 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 아이디로 소설 검색 테스트 실패")
    void testGetNovelByNovelId_실패() {
        log.info("소설 아이디로 소설 검색 테스트 시작");

        Long novelId = 1L;

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/novelId?novelId=" + novelId),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("소설 아이디로 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 이름으로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByNovelName_성공() {
        log.info("소설 이름으로 소설 리스트 검색 테스트 시작");

        String novelName = "화산";

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/novelName?novelName=" + novelName + "&page=" + page + "&size=" + size),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("소설 이름으로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 이름으로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByNovelName_실패() {
        log.info("소설 이름으로 소설 리스트 검색 테스트 시작");

        String novelName = "테스트입니다";

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/novelName?novelName=" + novelName + "&page=" + page + "&size=" + size),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("소설 이름으로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByNovelNameContainingAndTagIdList_성공() {
        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 시작");

        String novelName = "가";
        List<Long> tagIdList = List.of(4764L, 4765L);

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/novelName/and/tagId?novelName=" + novelName +
                        "&tagIdList=" + tagIdList.stream().map(Objects::toString).collect(Collectors.joining(",")) + "&page=" + page + "&size=" + size),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByNovelNameContainingAndTagIdList_실패() {
        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 시작");

        String novelName = "가";
        List<Long> tagIdList = List.of(1L, 2L);

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/novelName/and/tagId?novelName=" + novelName +
                        "&tagIdList=" + tagIdList.stream().map(Objects::toString).collect(Collectors.joining(",")) + "&page=" + page + "&size=" + size),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 종료");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
