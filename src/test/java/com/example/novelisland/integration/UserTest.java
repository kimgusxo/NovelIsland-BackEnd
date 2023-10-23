package com.example.novelisland.integration;

import com.example.novelisland.dto.UserDTO;
import com.example.novelisland.format.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("유저 업데이트 테스트")
    void testUpdateUser() {
        log.info("유저 업데이트 테스트 시작");

        UserDTO userDTO = new UserDTO(16996L, "testUser", "updatePassword");

        ResponseEntity<Message> response = restTemplate.exchange(
                createURLWithPort("/user/update"),
                HttpMethod.PUT,
                new HttpEntity<>(userDTO),
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("유저 업데이트 테스트 종료");
    }

    @Test
    @DisplayName("유저 삭제 테스트")
    void testDeleteUser() {
        log.info("유저 삭제 테스트 시작");

        Long userIndex = 16996L;

        ResponseEntity<Message> response = restTemplate.exchange(
                createURLWithPort("/user/delete?userIndex=" + userIndex),
                HttpMethod.DELETE,
                null,
                Message.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Message message = response.getBody();

        assert message != null;
        System.out.println(message.getMessage());

        log.info("유저 삭제 테스트 종료");
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
