package com.example.novelisland.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Document(indexName = "novel")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ElasticSearchNovel {
    @Id
    private Long novelId;

    private String novelExplanation;
}
