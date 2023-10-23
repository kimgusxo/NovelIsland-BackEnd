package com.example.novelisland.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name="TAG_TB")
public class Tag {

    @Id
    @GeneratedValue
    @Column(name = "TAG_ID")
    private Long tagId;

    @OneToMany(mappedBy = "tag",
            cascade = CascadeType.PERSIST)
    private List<Novel> novelList;

    @NotNull
    @Column(name = "TAG_CLASSIFICATION",
            unique = true)
    private String tagClassification;

}
