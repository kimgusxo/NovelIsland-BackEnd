package com.example.novelisland.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "novel")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ElasticSearchNovel {
    @Id
    @Field(name = "novel_id", type = FieldType.Long)
    private Long novelId;
    @Field(name = "novel_explanation", type = FieldType.Text, analyzer = "nori")
    private String novelExplanation;
}
