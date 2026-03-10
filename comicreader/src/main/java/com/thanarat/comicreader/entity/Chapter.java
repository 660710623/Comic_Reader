package com.thanarat.comicreader.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "chapters")
@Data
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer chapterNumber;
    private String title;

    @ManyToOne
    @JoinColumn(name = "comic_id")
    @JsonIgnore
    private Comic comic;


}

