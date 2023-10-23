package com.example.novelisland.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsertDataVO {

    private Long novelId;
    private String novelName;
    private String authorName;
    private String novelThumbNail;
    private String novelExplanation;
    private String tagClassification;

    public InsertDataVO() {
        
    }

    public InsertDataVO(Long novelId, String novelName, String authorName,
                        String novelThumbNail, String novelExplanation, String tagClassification) {
        this.novelId = novelId;
        this.novelName = novelName;
        this.authorName = authorName;
        this.novelThumbNail = novelThumbNail;
        this.novelExplanation = novelExplanation;
        this.tagClassification = tagClassification;
    }

}
