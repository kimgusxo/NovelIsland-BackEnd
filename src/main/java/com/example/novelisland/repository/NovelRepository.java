package com.example.novelisland.repository;

import com.example.novelisland.domain.Novel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelRepository extends JpaRepository<Novel, Long> {
    Boolean existsByNovelId(Long novelId);
    Novel findByNovelId(Long novelId);
    List<Novel> findByNovelNameContaining(String novelName, Pageable pageable);
    List<Novel> findByAuthor_AuthorId(Long authorId, Pageable pageable);

    @Query("select n from Novel n where n.novelName like %:novelName% and n.tag.tagId in :tags")
    List<Novel> findByNovelNameContainingAndTagIdList(@Param("novelName") String novelName, @Param("tags") List<Long> tagIdList, Pageable pageable);

    @Query(nativeQuery = true, value = "select * from novel_tb order by random() limit 3")
    List<Novel> findThreeNovelsByRandom();
}
