package com.thanarat.comicreader.entity;
import com.thanarat.comicreader.entity.Chapter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "comics")
@Data // ช่วยสร้าง getter/setter อัตโนมัติ (ต้องมี Lombok ในโปรเจค)
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;      // ชื่อเรื่อง
    private String author;     // ชื่อผู้แต่ง

    @OneToMany(mappedBy = "comic", cascade = CascadeType.ALL)
    private List<Chapter> chapters;
}