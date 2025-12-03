package com.example.novelisland.repository;

import com.example.novelisland.domain.Tag;
import com.example.novelisland.projection.NovelSummary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByTagClassification(String tagClassification);

    Optional<Tag> findByTagId(Long tagId);
    Optional<Tag> findByTagClassification(String tagClassification);

    @Query("select t.tagClassification as tagClassification, n.novelId as novelId, n.novelName as novelName, n.novelThumbNail as novelThumbNail, " +
            "n.novelExplanation as novelExplanation, a.authorName as authorName " +
            "from Tag t join t.novelList n join n.author a " +
            "where t.tagId = :tagId")
    List<NovelSummary> findNovelsByTagId(@Param("tagId") Long tagId, Pageable pageable);


    @Query("select t.tagClassification as tagClassification, n.novelId as novelId, n.novelName as novelName, n.novelThumbNail as novelThumbNail, " +
            "n.novelExplanation as novelExplanation, a.authorName as authorName " +
            "from Tag t join t.novelList n join n.author a " +
            "where t.tagClassification = :tagClassification")
    List<NovelSummary> findNovelsByTagClassification(@Param("tagClassification") String tagClassification, Pageable pageable);

}
