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

    @Autowired
    public BookMarkRepositoryTest(BookMarkRepository bookMarkRepository) {
        this.bookMarkRepository = bookMarkRepository;
    }

    @BeforeEach
    void setUp() {
        log.info("페이지 설정");

        pageable = PageRequest.of(0, 10);

        log.info("페이지 설정 완료");
    }


    @Test
    @DisplayName("북마크 존재유무 테스트")
    void testExistsByBookMarkIndex() {
        log.info("북마크 존재유무 테스트 시작");

        // given
        Long bookMarkId = 17070L;

        // when
        Boolean token = bookMarkRepository.existsByBookMarkId(bookMarkId);

        // then
        assertThat(token)
                .as("북마크가 존재하지 않습니다.")
                .isTrue();

        log.info("북마크 존재유무 테스트 종료");
    }

    @Test
    @DisplayName("유저의 북마크 존재유무 테스트")
    void testExistsByUserIndex() {
        log.info("유저의 북마크 존재유무 테스트 시작");

        // given
        Long userIndex = 16945L;

        // when
        Boolean token = bookMarkRepository.existsByUser_UserIndex(userIndex);

        // then
        assertThat(token)
                .as("북마크가 존재하지 않습니다.")
                .isTrue();

        log.info("유저의 북마크 존재유무 테스트 종료");
    }

    @Test
    @DisplayName("유저의 해당 소설 북마크 존재유무 테스트")
    void testExistsByUserIndexAndNovelId() {
        log.info("유저의 해당 소설 북마크 존재유무 테스트 시작");

        // given
        Long userIndex = 16945L;
        Long novelId = 4773L;

        // when
        Boolean token = bookMarkRepository.existsByUser_UserIndex(userIndex);

        // then
        assertThat(token)
                .as("북마크가 존재하지 않습니다.")
                .isTrue();

        log.info("유저의 해당 소설 북마크 존재유무 테스트 종료");
    }

    @Test
    @DisplayName("유저의 북마크 등록 테스트")
    void testCreateBookMark() {
        log.info("유저의 북마크 등록 테스트 시작");
        
        // given
        Long userIndex = 16945L;
        Long novelId = 4773L;

        // when
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

    // delete 문은 실제로 DB의 값의 영향을 줘 SpringBootTest로 진행해야 함
    @Test
    @DisplayName("유저의 북마크 해제 테스트")
    void testDeleteBookMark() {
        log.info("유저의 북마크 해제 테스트 시작");

        // given
        Long userIndex = 16945L;
        Long bookMarkId = 16974L;

        // when
        bookMarkRepository.deleteByBookMarkId(bookMarkId);

        // then
        List<BookMark> bookMarkList = bookMarkRepository.findByUser_UserIndex(userIndex, pageable);

        assertThat(bookMarkList)
                .extracting("bookMarkId")
                .as("북마크가 해제되지 않았습니다.")
                .doesNotContain(bookMarkId);

        log.info("유저의 북마크 해제 테스트 종료");
    }

    @Test
    @DisplayName("유저의 북마크 정보 가져오기 테스트")
    void testFindBookMarkListByUserIndex() {
        log.info("유저의 북마크 정보 가져오기 테스트 시작");
        
        // given
        Long userIndex = 16945L;

        // when
        Boolean token = bookMarkRepository.existsByUser_UserIndex(userIndex);

        // then
        List<BookMark> bookMarkList = bookMarkRepository.findByUser_UserIndex(userIndex, pageable);

        bookMarkList.forEach(b ->
                        System.out.println(
                                "북마크 아이디: " + b.getBookMarkId() + "\n" +
                                        "소설 아이디: " + b.getNovel().getNovelId())
        );

        log.info("유저의 북마크 정보 가져오기 테스트 종료");
    }
}
