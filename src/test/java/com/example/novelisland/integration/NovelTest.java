package com.example.novelisland.integration;

import com.example.novelisland.format.ErrorMessage;
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
        size = 32;

        log.info("페이지 변수 설정");
    }

    @Test
    @DisplayName("랜덤 소설 검색 테스트 성공")
    void testGetRandomNovels_성공() {
        log.info("랜덤 소설 검색 테스트 시작");

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/novel/get/random"),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("랜덤 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("인기순 소설 검색 테스트 성공")
    void testGetRankingNovels_성공() {
        log.info("인기순 소설 검색 테스트 시작");

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/novel/get/ranking?page=" + page + "&size=" + size),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("인기순 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("정렬된 소설 검색 테스트 성공")
    void testGetSortingNovels_성공() {
        log.info("정렬된 소설 검색 테스트 시작");

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/novel/get/sorting?page=" + page + "&size=" + size),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("정렬된 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 아이디로 소설 검색 테스트 성공")
    void testGetNovelByNovelId_성공() {
        log.info("소설 아이디로 소설 검색 테스트 시작");

        Long novelId = 69449L;

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

        ResponseEntity<ErrorMessage> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/novelId?novelId=" + novelId),
                ErrorMessage.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        ErrorMessage errorMessage = response.getBody();

        assert errorMessage != null;
        System.out.println(errorMessage.getMessage());

        log.info("소설 아이디로 소설 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 아이디 리스트로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByNovelIdList_성공() {
        log.info("소설 아이디 리스트로 소설 리스트 검색 테스트 시작");

        List<Long> novelIdList = List.of(69449L, 69450L);

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/novelIdList?novelIdList="
                        + novelIdList.stream().map(Objects::toString).collect(Collectors.joining(","))),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("소설 아이디 리스트로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 아이디 리스트로 소설 리스트 검색 테스트 실패")
    void testGetNovelsByNovelIdList_실패() {
        log.info("소설 아이디 리스트로 소설 리스트 검색 테스트 시작");

        List<Long> novelIdList = List.of(1L, 2L);

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/novelIdList?novelIdList="
                        + novelIdList.stream().map(Objects::toString).collect(Collectors.joining(","))),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        assertThat(message.getData()).isNull();

        log.info("소설 아이디 리스트로 소설 리스트 검색 테스트 종료");
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

        ResponseEntity<ErrorMessage> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/novelName?novelName=" + novelName + "&page=" + page + "&size=" + size),
                ErrorMessage.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        ErrorMessage errorMessage = response.getBody();

        assert errorMessage != null;
        System.out.println(errorMessage.getMessage());

        log.info("소설 이름으로 소설 리스트 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 아이디로 작가의 소설 목록 검색 테스트 성공")
    void testGetNovelsByAuthorId_성공() {
        log.info("작가 아이디로 작가의 소설 목록 검색 테스트 시작");

        Long authorId = 1L;

        ResponseEntity<Message> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/authorId?authorId=" + authorId + "&page=" + page + "&size=" + size),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("작가 아이디로 작가의 소설 목록 검색 테스트 종료");
    }

    @Test
    @DisplayName("작가 아이디로 작가의 소설 목록 검색 테스트 실패")
    void testGetNovelsByAuthorId_실패() {
        log.info("작가 아이디로 작가의 소설 목록 검색 테스트 시작");

        Long authorId = 100000L;

        ResponseEntity<ErrorMessage> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/authorId?authorId=" + authorId + "&page=" + page + "&size=" + size),
                ErrorMessage.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        ErrorMessage errorMessage = response.getBody();

        assert errorMessage != null;
        System.out.println(errorMessage.getMessage());

        log.info("작가 아이디로 작가의 소설 목록 검색 테스트 종료");
    }

    @Test
    @DisplayName("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 성공")
    void testGetNovelsByNovelNameContainingAndTagIdList_성공() {
        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 시작");

        String novelName = "가";
        List<Long> tagIdList = List.of(17879L, 17890L);

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

        ResponseEntity<ErrorMessage> response = restTemplate.getForEntity(
                createURLWithPort("/novel/find/novelName/and/tagId?novelName=" + novelName +
                        "&tagIdList=" + tagIdList.stream().map(Objects::toString).collect(Collectors.joining(",")) + "&page=" + page + "&size=" + size),
                ErrorMessage.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        ErrorMessage errorMessage = response.getBody();

        assert errorMessage != null;
        System.out.println(errorMessage.getMessage());

        log.info("소설 이름과 태그 리스트로 소설 리스트 검색 테스트 종료");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
