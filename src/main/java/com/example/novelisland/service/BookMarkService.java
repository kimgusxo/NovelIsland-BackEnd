package com.example.novelisland.service;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.domain.BookMark;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.domain.User;
import com.example.novelisland.dto.BookMarkDTO;
import com.example.novelisland.exception.bookmark.AlreadyExistBookMarkException;
import com.example.novelisland.exception.bookmark.NotExistBookMarkException;
import com.example.novelisland.exception.novel.NotExistNovelException;
import com.example.novelisland.repository.BookMarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookMarkService {

    private final BookMarkRepository bookMarkRepository;

    @Autowired
    public BookMarkService(BookMarkRepository bookMarkRepository) {
        this.bookMarkRepository = bookMarkRepository;
    }

    @Transactional
    public List<BookMarkDTO> getBookMarkListByUserIndex(Long userIndex, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        List<BookMark> bookMarkList = bookMarkRepository.findByUser_UserIndex(userIndex, pageable);

        List<BookMarkDTO> bookMarkDTOList = new ArrayList<>();

        if(bookMarkList.isEmpty()) {
            // 해당하는 북마크가 없을 때 예외처리
            return null;
        } else {
            for(BookMark bookMark : bookMarkList) {
                bookMarkDTOList.add(bookMark.toDTO());
            }
            return bookMarkDTOList;
        }
    }

    @Transactional
    public BookMarkDTO createBookMarkByUserIndex(Long userIndex, Long novelId) {
        // 북마크가 이미 있을때 예외처리 해야할듯
        Boolean token = bookMarkRepository.existsByUser_UserIndexAndNovel_NovelId(userIndex, novelId);

        if(!token) {
            BookMark bookMark = BookMark.builder()
                    .user(User.builder()
                            .userIndex(userIndex)
                            .build())
                    .novel(Novel.builder()
                            .novelId(novelId)
                            .build())
                    .build();

            BookMarkDTO bookMarkDTO = bookMarkRepository.save(bookMark).toDTO();

            return bookMarkDTO;
        } else {
            throw new AlreadyExistBookMarkException(ErrorCode.AlREADY_EXIST_BOOKMARK_TOKEN);
        }
    }

    @Transactional
    public void deleteBookMarkByBookMarkId(Long bookMarkId) {

        Boolean token = bookMarkRepository.existsByBookMarkId(bookMarkId);

        if(token) {
            bookMarkRepository.deleteByBookMarkId(bookMarkId);
        } else {
            throw new NotExistBookMarkException(ErrorCode.NOT_EXIST_BOOKMARK_TOKEN);
        }
    }

}
