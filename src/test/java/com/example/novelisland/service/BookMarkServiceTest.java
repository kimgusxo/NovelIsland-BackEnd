//package com.example.novelisland.service;
//
//import com.example.novelisland.domain.BookMark;
//import com.example.novelisland.domain.Novel;
//import com.example.novelisland.domain.User;
//import com.example.novelisland.dto.BookMarkDTO;
//import com.example.novelisland.exception.bookmark.AlreadyExistBookMarkException;
//import com.example.novelisland.exception.bookmark.NotExistBookMarkException;
//import com.example.novelisland.repository.BookMarkRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@Slf4j
//@ExtendWith(MockitoExtension.class)
//class BookMarkServiceTest {
//
//    @Mock
//    private BookMarkRepository bookMarkRepository;
//
//    @InjectMocks
//    private BookMarkService bookMarkService;
//
//    private BookMark bookMark;
//    private Pageable pageable;
//
//    private int page;
//    private int size;
//
//    @BeforeEach
//    void setUp() {
//        log.info("페이지 설정");
//
//        page = 0;
//        size = 32;
//
//        pageable = PageRequest.of(page, size);
//
//        log.info("페이지 설정 완료");
//
//        log.info("테스트 DTO 생성");
//
//        Long bookMarkId = 1L;
//        Long userIndex = 1L;
//        Long novelId = 1L;
//
//        bookMark = BookMark.builder()
//                .bookMarkId(bookMarkId)
//                .user(User.builder()
//                        .userIndex(userIndex)
//                        .build())
//                .novel(Novel.builder()
//                        .novelId(novelId)
//                        .build())
//                .build();
//
//        log.info("테스트 DTO 생성완료");
//    }
//
//    @Test
//    @DisplayName("유저 인덱스로 유저의 북마크 검색 테스트 성공")
//    void testGetBookMarkListByUserIndex_성공() {
//        log.info("유저 인덱스로 유저의 북마크 검색 테스트 시작");
//
//        // given
//        List<BookMark> bookMarkList = new ArrayList<>();
//        bookMarkList.add(bookMark);
//
//        when(bookMarkRepository.findByUser_UserIndex(bookMark.getUser().getUserIndex(), pageable)).thenReturn(bookMarkList);
//
//        // when
//        List<BookMarkDTO> bookMarkDTOList = bookMarkService.getBookMarkListByUserIndex(bookMark.getUser().getUserIndex(), page, size);
//
//        // then
//        assertThat(bookMarkDTOList).isNotNull();
//
//        log.info("유저 인덱스로 유저의 북마크 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("유저 인덱스로 유저의 북마크 검색 테스트 실패")
//    void testGetBookMarkListByUserIndex_실패() {
//        log.info("유저 인덱스로 유저의 북마크 검색 테스트 시작");
//
//        // given
//        when(bookMarkRepository.findByUser_UserIndex(bookMark.getUser().getUserIndex(), pageable)).thenReturn(new ArrayList<>());
//
//        // when
//        List<BookMarkDTO> bookMarkDTOList = bookMarkService.getBookMarkListByUserIndex(bookMark.getUser().getUserIndex(), page, size);
//
//        // then
//        assertThat(bookMarkDTOList).isNull();
//
//        log.info("유저 인덱스로 유저의 북마크 검색 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("유저 인덱스로 유저의 북마크 생성 테스트 성공")
//    void testCreateBookMarkByUserIndex_성공() {
//        log.info("유저 인덱스로 유저의 북마크 생성 테스트 시작");
//
//        // given
//        when(bookMarkRepository.existsByUser_UserIndexAndNovel_NovelId(bookMark.getUser().getUserIndex(), bookMark.getNovel().getNovelId()))
//                .thenReturn(false);
//        when(bookMarkRepository.save(any(BookMark.class))).thenReturn(bookMark);
//
//        // when
//        BookMarkDTO bookMarkDTO = bookMarkService.createBookMarkByUserIndex(bookMark.getUser().getUserIndex(), bookMark.getNovel().getNovelId());
//
//        // then
//        assertThat(bookMarkDTO).isNotNull();
//
//        log.info("유저 인덱스로 유저의 북마크 생성 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("유저 인덱스로 유저의 북마크 생성 테스트 실패")
//    void testCreateBookMarkByUserIndex_실패() {
//        log.info("유저 인덱스로 유저의 북마크 생성 테스트 시작");
//
//        // given
//        when(bookMarkRepository.existsByUser_UserIndexAndNovel_NovelId(bookMark.getUser().getUserIndex(), bookMark.getNovel().getNovelId()))
//                .thenReturn(true);
//
//        // when
//
//        // then
//        assertThatThrownBy(() -> bookMarkService.createBookMarkByUserIndex(bookMark.getUser().getUserIndex(), bookMark.getNovel().getNovelId()))
//                .isInstanceOf(AlreadyExistBookMarkException.class);
//
//        log.info("유저 인덱스로 유저의 북마크 생성 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("북마크 인덱스로 유저의 북마크 삭제 테스트 성공")
//    void testDeleteBookMarkByBookMarkIndex_성공() {
//        log.info("북마크 인덱스로 유저의 북마크 삭제 테스트 시작");
//
//        // given
//        when(bookMarkRepository.existsByBookMarkId(bookMark.getBookMarkId())).thenReturn(true);
//        doNothing().when(bookMarkRepository).deleteByBookMarkId(bookMark.getBookMarkId());
//
//        // when
//        bookMarkService.deleteBookMarkByBookMarkId(bookMark.getBookMarkId());
//
//        // then
//        verify(bookMarkRepository).deleteByBookMarkId(bookMark.getBookMarkId());
//
//        log.info("북마크 인덱스로 유저의 북마크 삭제 테스트 종료");
//    }
//
//    @Test
//    @DisplayName("북마크 인덱스로 유저의 북마크 삭제 테스트 성공")
//    void testDeleteBookMarkByBookMarkIndex_실패() {
//        log.info("북마크 인덱스로 유저의 북마크 삭제 테스트 시작");
//
//        // given
//        when(bookMarkRepository.existsByBookMarkId(bookMark.getBookMarkId())).thenReturn(false);
//
//        // when
//
//        // then
//        assertThatThrownBy(() -> bookMarkService.deleteBookMarkByBookMarkId(bookMark.getBookMarkId()))
//                .isInstanceOf(NotExistBookMarkException.class);
//
//        log.info("북마크 인덱스로 유저의 북마크 삭제 테스트 종료");
//    }
//
//
//}