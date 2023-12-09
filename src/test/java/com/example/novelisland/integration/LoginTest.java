//package com.example.novelisland.integration;
//import com.example.novelisland.dto.LoginDTO;
//import com.example.novelisland.format.ErrorMessage;
//import com.example.novelisland.format.Message;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.concurrent.*;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@Slf4j
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class LoginTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    @DisplayName("중복 사용자가 있는지 체크 테스트 성공")
//    void testDuplicateCheck_성공() {
//        log.info("중복 사용자가 있는지 체크 테스트 시작");
//
//        String userId = "test";
//
//        ResponseEntity<Message> response = restTemplate.getForEntity(
//                createURLWithPort("/login/duplicateCheck?userId=" + userId),
//                Message.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        Message message = response.getBody();
//
//        assert message != null;
//        System.out.println(message.getMessage());
//
//        log.info("중복 사용자가 있는지 체크 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("새로운 사용자로 회원가입 테스트")
//    void testSignUp_새로운사용자() {
//        log.info("새로운 사용자로 회원가입 테스트 시작");
//
//        LoginDTO loginDTO = new LoginDTO("testUser", "password123");
//
//        ResponseEntity<Message> response = restTemplate.postForEntity(
//                createURLWithPort("/login/signUp"),
//                loginDTO,
//                Message.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        Message message = response.getBody();
//
//        assertThat(message).isNotNull();
//        assertThat(message.getData()).isNotNull();
//
//        System.out.println(message.getData());
//
//        log.info("새로운 사용자로 회원가입 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("중복 사용자로 회원가입 테스트")
//    void testSignUp_중복사용자() {
//        log.info("중복 사용자로 회원가입 테스트 시작");
//
//        LoginDTO loginDTO = new LoginDTO("testUser", "password123");
//
//        // 중복 회원가입 시도
//        ResponseEntity<ErrorMessage> response2 = restTemplate.postForEntity(
//                createURLWithPort("/login/signUp"),
//                loginDTO,
//                ErrorMessage.class
//        );
//
//        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//
//        ErrorMessage errorMessage = response2.getBody();
//
//        assert errorMessage != null;
//        System.out.println(errorMessage.getMessage());
//
//        log.info("중복 사용자로 회원가입 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("로그인 테스트 성공")
//    void testSignIn_성공() {
//        log.info("로그인 테스트 시작");
//
//        LoginDTO loginDTO = new LoginDTO("testUser", "password123");
//
//        // 로그인 시도
//        ResponseEntity<Message> signInResponse = restTemplate.postForEntity(
//                createURLWithPort("/login/signIn"),
//                loginDTO,
//                Message.class
//        );
//
//        assertThat(signInResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        Message message = signInResponse.getBody();
//
//        assertThat(message).isNotNull();
//        assertThat(message.getData()).isNotNull();
//
//        System.out.println(message.getData());
//
//        log.info("로그인 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("잘못된 아이디로 로그인 테스트")
//    void testSignIn_잘못된아이디() {
//        log.info("잘못된 아이디로 로그인 테스트 시작");
//
//        LoginDTO loginDTO = new LoginDTO("nonExistentUser", "password123");
//
//        ResponseEntity<ErrorMessage> response = restTemplate.postForEntity(
//                createURLWithPort("/login/signIn"),
//                loginDTO,
//                ErrorMessage.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//
//        ErrorMessage errorMessage = response.getBody();
//
//        assert errorMessage != null;
//        System.out.println(errorMessage.getMessage());
//
//        log.info("잘못된 아이디로 로그인 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("잘못된 비밀번호로 로그인 테스트")
//    void testSignIn_잘못된비밀번호() {
//        log.info("잘못된 비밀번호로 로그인 테스트 시작");
//
//        LoginDTO loginDTO = new LoginDTO("testUser", "wrongPassword");
//
//        // 잘못된 비밀번호로 로그인 시도
//        ResponseEntity<ErrorMessage> response = restTemplate.postForEntity(
//                createURLWithPort("/login/signIn"),
//                loginDTO,
//                ErrorMessage.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//
//        ErrorMessage errorMessage = response.getBody();
//
//        assert errorMessage != null;
//        System.out.println(errorMessage.getMessage());
//
//        log.info("잘못된 비밀번호로 로그인 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("동시 사용자로 회원가입 테스트")
//    void testSignUp_동시사용자() throws InterruptedException, ExecutionException {
//        log.info("동시 사용자로 회원가입 테스트 시작");
//
//        LoginDTO loginDTO1 = new LoginDTO("test106", "password123");
//        LoginDTO loginDTO2 = new LoginDTO("test106", "password456");
//        LoginDTO loginDTO3 = new LoginDTO("test106", "password123");
//        LoginDTO loginDTO4 = new LoginDTO("test106", "password456");
//        LoginDTO loginDTO5 = new LoginDTO("test106", "password123");
//        LoginDTO loginDTO6 = new LoginDTO("test106", "password456");
//
//        ExecutorService executorService = Executors.newFixedThreadPool(6);
//
//        // Callable을 사용하여 각각의 회원가입 요청을 별도의 스레드에서 실행
//        Callable<ResponseEntity<Message>> signUpTask1 = () -> restTemplate.postForEntity(
//                createURLWithPort("/login/signUp"),
//                loginDTO1,
//                Message.class
//        );
//
//        Callable<ResponseEntity<Message>> signUpTask2 = () -> restTemplate.postForEntity(
//                createURLWithPort("/login/signUp"),
//                loginDTO2,
//                Message.class
//        );
//        Callable<ResponseEntity<Message>> signUpTask3 = () -> restTemplate.postForEntity(
//                createURLWithPort("/login/signUp"),
//                loginDTO3,
//                Message.class
//        );
//
//        Callable<ResponseEntity<Message>> signUpTask4 = () -> restTemplate.postForEntity(
//                createURLWithPort("/login/signUp"),
//                loginDTO4,
//                Message.class
//        );
//
//        Callable<ResponseEntity<Message>> signUpTask5 = () -> restTemplate.postForEntity(
//                createURLWithPort("/login/signUp"),
//                loginDTO5,
//                Message.class
//        );
//
//        Callable<ResponseEntity<Message>> signUpTask6 = () -> restTemplate.postForEntity(
//                createURLWithPort("/login/signUp"),
//                loginDTO6,
//                Message.class
//        );
//
//
//        // 동시에 실행하고 결과를 기다림
//        Future<ResponseEntity<Message>> future1 = executorService.submit(signUpTask1);
//        Future<ResponseEntity<Message>> future2 = executorService.submit(signUpTask2);
//        Future<ResponseEntity<Message>> future3 = executorService.submit(signUpTask3);
//        Future<ResponseEntity<Message>> future4 = executorService.submit(signUpTask4);
//        Future<ResponseEntity<Message>> future5 = executorService.submit(signUpTask5);
//        Future<ResponseEntity<Message>> future6 = executorService.submit(signUpTask6);
//
//        // 결과를 가져옴
//        ResponseEntity<Message> response1 = future1.get();
//        ResponseEntity<Message> response2 = future2.get();
//        ResponseEntity<Message> response3 = future3.get();
//        ResponseEntity<Message> response4 = future4.get();
//        ResponseEntity<Message> response5 = future5.get();
//        ResponseEntity<Message> response6 = future6.get();
//
//        // 테스트 코드 작성
//        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response3.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response4.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response5.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response6.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        // 나머지 테스트 코드 작성...
//        executorService.shutdown();
//
//        log.info("동시 사용자로 회원가입 테스트 종료");
//    }
//
//    private String createURLWithPort(String uri) {
//        return "http://localhost:" + port + uri;
//    }
//}
