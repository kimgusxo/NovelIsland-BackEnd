package com.example.novelisland.service;

import com.example.novelisland.description.ErrorCode;
import com.example.novelisland.domain.Tag;
import com.example.novelisland.dto.NovelSummaryDTO;
import com.example.novelisland.dto.TagDTO;
import com.example.novelisland.exception.novel.NotExistNovelException;
import com.example.novelisland.exception.tag.NotExistTagException;
import com.example.novelisland.projection.NovelSummary;
import com.example.novelisland.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    public List<TagDTO> getSortingTags() {
        Sort sort = Sort.by(Sort.Order.asc("tagClassification"));

        List<Tag> tagList = tagRepository.findAll(sort);

        List<TagDTO> tagDTOList = new ArrayList<>();

        for(Tag tag : tagList) {
            tagDTOList.add(tag.toDTO());
        }

        return tagDTOList;
    }

    @Transactional
    public TagDTO getTagByTagId(Long tagId) {

        Boolean token = tagRepository.existsByTagId(tagId);

        if(token) {
            TagDTO tagDTO = tagRepository.findByTagId(tagId).toDTO();
            return tagDTO;
        } else {
            throw new NotExistTagException(ErrorCode.NOT_EXIST_TAG_TOKEN);
        }
    }

    @Transactional
    public List<NovelSummaryDTO> getNovelsByTagId(Long tagId, int page, int size) {

        Boolean token = tagRepository.existsByTagId(tagId);

        if(token) {
            Pageable pageable = PageRequest.of(page, size);

            // 태그는 무조건 존재하므로 예외처리 X
            List<NovelSummary> novelSummaryList = tagRepository.findNovelsByTagId(tagId, pageable);

            List<NovelSummaryDTO> novelSummaryDTOList = new ArrayList<>();

            for(NovelSummary ns : novelSummaryList) {
                novelSummaryDTOList.add(NovelSummaryDTO.builder()
                        .novelId(ns.getNovelId())
                        .tagClassification(ns.getTagClassification())
                        .authorName(ns.getAuthorName())
                        .novelName(ns.getNovelName())
                        .novelThumbnail(ns.getNovelThumbnail())
                        .novelExplanation(ns.getNovelExplanation())
                        .build());
            }
            return novelSummaryDTOList;
        } else {
            throw new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN);
        }
    }


    @Transactional
    public List<NovelSummaryDTO> getNovelsByTagClassification(String tagClassification, int page, int size) {

        Boolean token = tagRepository.existsByTagClassification(tagClassification);

        if(token) {
            Pageable pageable = PageRequest.of(page, size);

            // 검색한 태그가 맞는지 확인
            List<NovelSummary> novelSummaryList = tagRepository.findNovelsByTagClassification(tagClassification, pageable);

            List<NovelSummaryDTO> novelSummaryDTOList = new ArrayList<>();

            for(NovelSummary ns : novelSummaryList) {
                novelSummaryDTOList.add(NovelSummaryDTO.builder()
                        .novelId(ns.getNovelId())
                        .tagClassification(ns.getTagClassification())
                        .authorName(ns.getAuthorName())
                        .novelName(ns.getNovelName())
                        .novelThumbnail(ns.getNovelThumbnail())
                        .novelExplanation(ns.getNovelExplanation())
                        .build());
            }
            return novelSummaryDTOList;
        } else {
            throw new NotExistNovelException(ErrorCode.NOT_EXIST_NOVEL_TOKEN);
        }
    }

}
