package com.thanarat.comicreader.repository;

import com.thanarat.comicreader.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    long countByComicId(Long comicId);

    List<Chapter> findByComicIdOrderByChapterNumberAsc(Long comicId);


}
