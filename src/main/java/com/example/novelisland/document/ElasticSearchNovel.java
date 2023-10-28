package com.example.novelisland.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "novel")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ElasticSearchNovel {
    @Id
    @Field(name = "novel_id")
    private Long novelId;
    @Field(name = "novel_explanation")
    private String novelExplanation;
}
