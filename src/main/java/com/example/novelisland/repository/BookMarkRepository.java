package com.example.novelisland.repository;

import com.example.novelisland.domain.BookMark;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {

    boolean existsByUser_UserIndex(Long userIndex);
    boolean existsByUser_UserIndexAndNovel_NovelId(Long userIndex, Long novelId);

    List<BookMark> findByUser_UserIndex(Long userIndex, Pageable pageable);
}
