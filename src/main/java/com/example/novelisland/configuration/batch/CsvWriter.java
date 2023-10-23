package com.example.novelisland.configuration.batch;

import com.example.novelisland.domain.Author;
import com.example.novelisland.domain.Novel;
import com.example.novelisland.domain.Tag;
import com.example.novelisland.repository.AuthorRepository;
import com.example.novelisland.repository.NovelRepository;
import com.example.novelisland.repository.TagRepository;
import com.example.novelisland.utils.DeduplicationUtils;
import com.example.novelisland.vo.InsertDataVO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CsvWriter {

    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;
    private final NovelRepository novelRepository;

    @Bean
    public ItemWriter<InsertDataVO> compositeItemWriter() {
        CompositeItemWriter<InsertDataVO> compositeItemWriter = new CompositeItemWriter<>();
        compositeItemWriter.setDelegates(List.of(authorWriter(), tagWriter(), novelWriter()));
        return compositeItemWriter;
    }

    @Transactional
    public ItemWriter<InsertDataVO> authorWriter() {
        return items -> {
            List<Author> authors = new ArrayList<>();
            for (InsertDataVO insertDataVO : items) {
                Author author = Author.builder()
                        .authorName(insertDataVO.getAuthorName())
                        .build();
                authors.add(author);
            }
            List<Author> authorList = DeduplicationUtils.deduplication(authors, Author::getAuthorName);
            authorRepository.saveAll(authorList);
        };
    }

    @Transactional
    public ItemWriter<InsertDataVO> tagWriter() {
        return items -> {
            List<Tag> tags = new ArrayList<>();
            for (InsertDataVO insertDataVO : items) {
                Tag tag = Tag.builder()
                        .tagClassification(insertDataVO.getTagClassification())
                        .build();
                tags.add(tag);
            }
            List<Tag> tagList = DeduplicationUtils.deduplication(tags, Tag::getTagClassification);
            tagRepository.saveAll(tagList);
        };
    }

    @Transactional
    public ItemWriter<InsertDataVO> novelWriter() {
        return items -> {
            List<Novel> novels = new ArrayList<>();
            for (InsertDataVO insertDataVO : items) {
                Author author = authorRepository.findByAuthorName(insertDataVO.getAuthorName());
                Tag tag = tagRepository.findByTagClassification(insertDataVO.getTagClassification());

                Novel novel = Novel.builder()
                        .novelId(insertDataVO.getNovelId())
                        .author(author)
                        .novelName(insertDataVO.getNovelName())
                        .novelThumbNail(insertDataVO.getNovelThumbNail())
                        .novelExplanation(insertDataVO.getNovelExplanation())
                        .tag(tag)
                        .build();
                novels.add(novel);
            }
            novelRepository.saveAll(novels);
        };
    }
}
