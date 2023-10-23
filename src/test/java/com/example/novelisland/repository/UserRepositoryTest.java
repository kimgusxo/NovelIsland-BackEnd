package com.example.novelisland.repository;

import com.example.novelisland.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    private User user;

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @BeforeEach
    void setUp() {
        log.info("테스트 유저 생성중");

        user = User.builder()
                .userId("test1")
                .userPassword("123")
                .build();

        log.info("테스트 유저 생성완료");
    }

    @AfterEach
    void cleanUp() {
        // 테스트 데이터베이스 정리
        userRepository.delete(user);
    }

    // @Nested를 사용하여 가짜 유저도 만들어서 테스트 진행해봄

    @Test
    @DisplayName("회원가입")
    void signUp() {
        log.info("회원가입 테스트 시작");
        
        // given
        Boolean tokenId = userRepository.existsByUserId(user.getUserId());

        // when
        if(!tokenId) {
            User savedUser = userRepository.save(user);

            // then
            assertThat(savedUser)
                    .as("아이디가 일치하지 않습니다.")
                    .hasFieldOrPropertyWithValue("userId", user.getUserId())
                    .as("비밀번호가 일치하지 않습니다.")
                    .hasFieldOrPropertyWithValue("userPassword", user.getUserPassword());

            log.info("회원가입 테스트 종료");
        } else {
            fail("이미 존재하는 아이디입니다.");
        }
    }

    @Test
    @DisplayName("로그인")
    void login() {
        log.info("로그인 테스트 시작");

        // given
        Boolean tokenId = userRepository.existsByUserId(user.getUserId());

        // when
        if(tokenId) {
            User loginUser = userRepository.findByUserId(user.getUserId());

            // then
            assertThat(loginUser.getUserPassword())
                    .as("비밀번호가 틀렸습니다.")
                    .isEqualTo(user.getUserPassword());

            log.info("로그인 테스트 종료");
        } else {
            fail("아이디가 존재하지 않습니다.");
        }
    }

    @Test
    @DisplayName("유저 아이디로 유저의 존재여부 확인")
    void existsByUserId() {
        log.info("유저 존재여부 테스트 시작");

        // given

        // when
        Boolean token = userRepository.existsByUserId(user.getUserId());

        // then
        assertThat(token)
                .as("유저가 존재하지 않습니다.")
                .isTrue();

        log.info("유저 존재여부 테스트 종료");
    }

    @Test
    @DisplayName("유저 아이디로 유저 검색")
    void findUserById() {
        log.info("유저 아이디로 유저 검색 테스트 시작");

        // given
        Boolean token = userRepository.existsByUserId(user.getUserId());

        // when
        if(token) {
            User findUser = userRepository.findByUserId(user.getUserId());

            // then
            System.out.println("유저 인덱스: " + findUser.getUserIndex());
            System.out.println("유저 아이디: " + findUser.getUserId());
            System.out.println("유저 비밀번호: " + findUser.getUserPassword());

            log.info("유저 아이디로 유저 검색 테스트 종료");
        } else {
            fail("검색한 유저가 존재하지 않습니다.");
        }
    }

    @Test
    @DisplayName("유저 아이디로 유저 삭제")
    void deleteUser() {
        log.info("유저 아이디로 유저 삭제 테스트 시작");

        // given
        Boolean tokenId = userRepository.existsByUserId(user.getUserId());

        // when
        if(tokenId) {
            User deleteUser = userRepository.findByUserId(user.getUserId());

            userRepository.deleteById(deleteUser.getUserIndex());

            // then
            List<User> userList = userRepository.findAll();

            userList.stream().forEach(u ->
                    System.out.println(
                            "유저 번호: " + u.getUserIndex() + "\n" +
                            "유저 아이디: " + u.getUserId() + "\n" +
                            "유저 비밀번호: " + u.getUserPassword() + "\n"
                    ));

            log.info("유저 아이디로 유저 삭제 테스트 종료");

        } else {
            fail("삭제할 유저가 존재하지 않습니다.");
        }
    }

    // update 문은 실제로 DB의 값의 영향을 줘 @SpringBootTest로 진행해야 함
    @Test
    @DisplayName("유저 정보 업데이트")
    void updateUser() {
        log.info("유저 정보 업데이트 테스트 시작");

        // given
        Long userIndex = 16945L;
        User updateUser = userRepository.findByUserIndex(userIndex);

        String updateUserPassword = "1234";

        // when
        if(!updateUser.getUserPassword().equals(updateUserPassword)) {
            userRepository.updateUser(updateUserPassword, updateUser.getUserIndex());
        } else {
            fail("이전 비밀번호와 동일한 비밀번호입니다.");
        }

        // then
        User updatedUser = userRepository.findByUserIndex(updateUser.getUserIndex());

        assertThat(updateUser.getUserPassword())
                .as("비밀번호 변경에 실패하였습니다.")
                .isEqualTo(updatedUser.getUserPassword());

        log.info("유저 정보 업데이트 테스트 종료");
    }

    @Test
    @DisplayName("유저 리스트 가져오기(관리자)")
    void findAll() {
        log.info("유저 리스트 가져오기 테스트 시작");

        // given

        // when
        List<User> userList = userRepository.findAll();

        // then
        assertThat(userList)
                .as("유저가 존재하지 않습니다.")
                .isNotEmpty();

        userList.stream().forEach(u ->
                System.out.println(
                        "유저 번호: " + u.getUserIndex() + "\n" +
                        "유저 아이디: " + u.getUserId() + "\n" +
                        "유저 비밀번호: " + u.getUserPassword() + "\n"
                        ));

        log.info("유저 리스트 가져오기 테스트 종료");
    }

}
