package com.example.novelisland.service;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.domain.Author;
import com.example.novelisland.dto.AuthorDTO;
import com.example.novelisland.exception.author.NotExistAuthorException;
import com.example.novelisland.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<AuthorDTO> getSortingAuthor(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("authorName")));
        Page<Author> authorPage = authorRepository.findAll(pageable);

        if (authorPage.isEmpty()) {
            throw new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN);
        }

        return authorPage.stream().map(Author::toDTO).collect(Collectors.toList());
    }

    public AuthorDTO getAuthorByAuthorId(Long authorId) {
        return authorRepository.findByAuthorId(authorId)
                .map(Author::toDTO)
                .orElseThrow(() -> new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN));
    }

    public List<AuthorDTO> getAuthorByAuthorName(String authorName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Author> authorList = authorRepository.findByAuthorNameContaining(authorName, pageable);

        if(authorList.isEmpty()) {
            throw new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN);
        }

        return  authorList.stream().map(Author::toDTO).collect(Collectors.toList());
    }

}
