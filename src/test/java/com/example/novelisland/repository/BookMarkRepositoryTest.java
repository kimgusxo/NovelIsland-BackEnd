package com.example.novelisland.repository;

import com.example.novelisland.domain.BookMark;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookMarkRepositoryTest {

    private final BookMarkRepository bookMarkRepository;

    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);
    }

    @Autowired
    public BookMarkRepositoryTest(BookMarkRepository bookMarkRepository) {
        this.bookMarkRepository = bookMarkRepository;
    }

    @Test
    @DisplayName("유저의 북마크 등록")
    void createBookMark() {
        log.info("유저의 북마크 등록 테스트 시작");
        
        // given
        Long userIndex = 16945L;
        Long novelId = 4773L;

        // when
        List<BookMark> bookMarkList = bookMarkRepository.findByUser_UserIndex(userIndex, pageable);

        // Novel이 필요하지 않는데도 가져오는 경향이 있음
        boolean existToken = bookMarkList.stream()
                .anyMatch(b -> b.getNovel().getNovelId() == 4773L);

        if(existToken) {
            fail("이미 북마크가 등록되어 있습니다.");
        } else {
            BookMark bookMark = BookMark.builder()
                    .user(User.builder()
                            .userIndex(userIndex)
                            .build())
                    .novel(Novel.builder()
                            .novelId(novelId)
                            .build())
                    .build();

            BookMark createdBookMark = bookMarkRepository.save(bookMark);

            // then
            assertThat(createdBookMark)
                    .as("북마크가 등록되지 않았습니다.")
                    .isEqualTo(bookMark);

            log.info("유저의 북마크 등록 테스트 종료");
        }
    }

    // delete 문은 실제로 DB의 값의 영향을 줘 SpringBootTest로 진행해야 함
    @Test
    @Transactional
    @DisplayName("유저의 북마크 해제")
    void deleteBookMark() {
        log.info("유저의 북마크 해제 테스트 시작");

        // given
        Long userIndex = 16945L;
        Long bookMarkId = 16974L;

        // when
        boolean token = bookMarkRepository.existsById(bookMarkId);

        // then
        if(token) {
            bookMarkRepository.deleteByBookMarkId(bookMarkId);

            List<BookMark> bookMarkList = bookMarkRepository.findByUser_UserIndex(userIndex, pageable);

            assertThat(bookMarkList)
                    .extracting("bookMarkId")
                    .as("북마크가 해제되지 않았습니다.")
                    .doesNotContain(bookMarkId);

            log.info("유저의 북마크 해제 테스트 종료");
        } else {
            fail("북마크가 존재하지 않습니다.");
        }
    }


    @Test
    @DisplayName("유저가 북마크를 한 소설이 있는지 확인")
    void existsBookMarkByUserIndex() {
        log.info("유저가 북마크를 한 소설이 있는지 확인 테스트 시작");
        
        // given
        Long userIndex = 16945L;

        // when
        Boolean token = bookMarkRepository.existsByUser_UserIndex(userIndex);

        // then
        assertThat(token)
                .as("북마크가 존재하지 않습니다.")
                .isTrue();
        
        log.info("유저가 북마크를 한 소설이 있는지 확인 테스트 종료");
    }

    @Test
    @DisplayName("유저의 북마크 정보 가져오기")
    void findBookMarkListByUserIndex() {
        log.info("유저의 북마크 정보 가져오기 테스트 시작");
        
        // given
        Long userIndex = 16945L;

        // when
        Boolean token = bookMarkRepository.existsByUser_UserIndex(userIndex);

        // then
        if(token) {
            List<BookMark> bookMarkList = bookMarkRepository.findByUser_UserIndex(userIndex, pageable);

            bookMarkList.forEach(b ->
                            System.out.println(
                                    "북마크 아이디: " + b.getBookMarkId() + "\n" +
                                    "소설 아이디: " + b.getNovel().getNovelId())
                    );

            log.info("유저의 북마크 정보 가져오기 테스트 종료");
        } else {
            fail("유저의 북마크가 존재하지 않습니다.");
        }
    }
}
