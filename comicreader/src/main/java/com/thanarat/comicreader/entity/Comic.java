package com.thanarat.comicreader.entity;
import com.thanarat.comicreader.entity.Chapter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "comics")
@Data
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @OneToMany(mappedBy = "comic", cascade = CascadeType.ALL)
    private List<Chapter> chapters;
}