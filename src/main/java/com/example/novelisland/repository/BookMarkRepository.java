package com.example.novelisland.repository;

import com.example.novelisland.domain.BookMark;
import com.example.novelisland.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    Boolean existsByBookMarkId(Long bookMarkId);
    Boolean existsByUser_UserIndex(Long userIndex);
    Boolean existsByUser_UserIndexAndNovel_NovelId(Long userIndex, Long novelId);
    void deleteByBookMarkId(Long bookMarkId);
    List<BookMark> findByUser_UserIndex(Long userIndex, Pageable pageable);
}
