package com.example.novelisland.repository;

import com.example.novelisland.domain.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Boolean existsByAuthorId(Long authorId);
    Boolean existsByAuthorName(String authorName);
    Author findByAuthorId(Long authorId);
    Author findByAuthorName(String authorName);
    List<Author> findByAuthorNameContaining(String authorName, Pageable pageable);
}
