package com.example.novelisland.service;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.domain.Author;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.dto.AuthorDTO;
import com.example.novelisland.dto.NovelDTO;
import com.example.novelisland.exception.author.NotExistAuthorException;
import com.example.novelisland.exception.novel.NotExistNovelException;
import com.example.novelisland.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional
    public List<AuthorDTO> getSortingAuthor() {
        int page = 0;
        int size = 32;

        // Paging 설정
        Sort sort = Sort.by(Sort.Order.asc("authorName"));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Author> authorList = authorRepository.findAll(pageable);

        List<AuthorDTO> authorDTOList = new ArrayList<>();

        if(authorList.isEmpty()) {
            throw new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN);
        } else {
            for(Author author : authorList) {
                authorDTOList.add(author.toDTO());
            }
        }

        return authorDTOList;
    }

    @Transactional
    public AuthorDTO getAuthorByAuthorId(Long authorId) {
        Boolean token  = authorRepository.existsByAuthorId(authorId);

        if(token) {
            Author author = authorRepository.findByAuthorId(authorId);
            return author.toDTO();
        } else {
            throw new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN);
        }
    }

    @Transactional
    public List<AuthorDTO> getAuthorByAuthorName(String authorName, int page, int size) {
        // Paging 설정
        Pageable pageable = PageRequest.of(page, size);

        List<Author> authorList = authorRepository.findByAuthorNameContaining(authorName, pageable);

        List<AuthorDTO> authorDTOList = new ArrayList<>();

        if(authorList.isEmpty()) {
            throw new NotExistAuthorException(ErrorCode.NOT_EXIST_AUTHOR_TOKEN);
        } else {
            for(Author author : authorList) {
                authorDTOList.add(author.toDTO());
            }
            return authorDTOList;
        }
    }

}
